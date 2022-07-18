#!/bin/bash

#
#$2 = ${VERSION}
#$2 = ${BITBUCKET_BRANCH}.${BITBUCKET_BUILD_NUMBER}
docker_build(){
    TAG=$1
    export DOCKER_IMAGE=$DOCKER_REPO/$APPLICATION:$TAG
    docker login $DOCKER_REPO -u=$NEXUS_USERNAME -p=$NEXUS_PASSWORD
    docker build -f build/Dockerfile . -t ${DOCKER_IMAGE}
    docker push ${DOCKER_IMAGE}
}

#criar arquivo parar realizar deploy no ambiente de testes
deploy(){
cat <<EOF > deploy.sh
    #!/bin/bash
    sudo su -c "
        sed -i \"/VERSAO_`echo $APPLICATION | sed -e \"s/\(.*\)/\U\1/\"`/d\" $COMPOSE_LOCATION/.env
        sed -i \"1s/^/VERSAO_`echo $APPLICATION | sed -e \"s/\(.*\)/\U\1/\"`=${BITBUCKET_BRANCH}.${BITBUCKET_BUILD_NUMBER}\n/\" $COMPOSE_LOCATION/.env
        cp $COMPOSE_LOCATION/.env /tmp/.deploy
        sed -i \"/#/d\" /tmp/.deploy
        sed -i \"/^$/d\" /tmp/.deploy 
        sed -i \"s/^/export /\" /tmp/.deploy
        . /tmp/.deploy
        docker-compose -f $COMPOSE_LOCATION/docker-compose.yml stop $APPLICATION
        docker-compose -f $COMPOSE_LOCATION/docker-compose.yml rm -f $APPLICATION
        docker-compose -f $COMPOSE_LOCATION/docker-compose.yml up -d $APPLICATION
    " -s /bin/sh root
EOF
}

#Função para buscar a versão caso a aplicação possua apenas um projeto
#Quando for utilizar no pipeline: source build/scripts.sh && get_version para que a variável VERSION seja
#ao contexto do bash geral
get_version(){
    grep "<version>" pom.xml > tmp_version
    sed -ni 1p tmp_version
    sed -i 's/<version>//g' tmp_version
    sed -i 's/<\/version>//g' tmp_version
    sed -i 's/^[ \t]*//;s/[ \t]*$//' tmp_version
    export VERSION=$(cat tmp_version)
    rm tmp_version
}

#Função para buscar a versão caso a aplicação possua um projeto parent
#Quando for utilizar no pipeline: source build/scripts.sh && get_version_parent para que a variável VERSION seja
#ao contexto do bash geral
get_version_parent() {
     grep "<global.version>" pom.xml > tmp_version
     sed -i 's/<global.version>//g' tmp_version
     sed -i 's/<\/global.version>//g' tmp_version
     sed -i 's/^[ \t]*//;s/[ \t]*$//' tmp_version
     sed -i 's/\r$//' tmp_version
     export VERSION=$(cat tmp_version)
     rm tmp_version
}

#build passando o número da build para aparecer no rodapé do sistema
build_only_test(){
    echo "version=${BITBUCKET_BRANCH}.${BITBUCKET_BUILD_NUMBER}" > 'src/main/resources/version.properties' && mvn package -Pproducao
}

git_merge(){
    git config remote.origin.fetch "+refs/heads/*:refs/remotes/origin/*"
    git remote update
    git fetch --all
    git checkout test
    git merge --no-edit --progress ${BITBUCKET_BRANCH} &> merge.log
    conflict_check=`grep "CONFLICT" merge.log`
    if [[ "$conflict_check" == "" ]]; then
      echo "Sem conflito"
      cat merge.log
      rm merge.log
      git push origin test
    else
      echo "Conflito encontrado"
      cat merge.log
      rm merge.log
      exit 1
    fi

}

git_tag(){
    git tag -am "Automatically generated" ${VERSION}
    git push origin ${VERSION}
}

#Create liquibase tag with sql script
#Check if argument $1 exists, prepending the script's file path with its value or leaving it as is if not there.
liquibase_create_tag() {
  TAG_NAME=liquibase-${APPLICATION}-${BITBUCKET_BRANCH}.${BITBUCKET_BUILD_NUMBER}
  if [ -n "$1" ];
  then
    git add -f $1/src/main/resources/liquibase-*.sql
  else
    git add -f src/main/resources/liquibase-*.sql
  fi
  git commit -m "[skip ci] Atualiza o Script SQL para o versionamento do banco de dados com liquibase. Usado somente em casos em que a migração precisa ser feita manualmente. Gerado automaticamente durante o build ${BITBUCKET_BUILD_NUMBER}."
  git tag -am "Tagging for release ${BITBUCKET_BRANCH}.${BITBUCKET_BUILD_NUMBER}" ${TAG_NAME}
  git push --follow-tags
}

opt=$1

case $opt in
    build_only_test) 
        build_only_test;;
    deploy) 
        deploy;;
    get_version) 
        get_version;;
    docker_build) 
        docker_build;;
    git_merge) 
        git_merge;;
    git_tag) 
        git_tag;;
esac

