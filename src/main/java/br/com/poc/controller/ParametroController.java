/*package br.com.poc.controller;

import br.com.poc.enuns.TipoOperacaoEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.poc.exception.AsqPersistenciaException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parametro")
@Api(value = "Protótipo de arquitetura para API REST ASQ")
@CrossOrigin(origins = "*") // definição de quais domínios poderam acessar esta API
public class ParametroController {

        @Autowired
        private ParametroService parametroService;

        @Autowired
        private AuditoriaController auditoriaController;

        private static final Logger log = Logger.getLogger(ParametroService.class);

        @GetMapping("/listar")
        @ApiOperation(value = "Retorna uma lista de dados da Entidade Parametro")
        public List<Parametro> listarParametros(){

            try {

                return this.parametroService.listar();

            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new AsqPersistenciaException(e.getLocalizedMessage());
            }
        }



        @PostMapping("/alterar")
        @ApiOperation(value = "Alera os dados da entidade Parametro")
        public void alterarParametros(@RequestBody Parametro parametro, String usuario){

            try {

                this.parametroService.alterar(parametro);

                registraAuditoria(usuario, TipoOperacaoEnum.ALTERAR.getNome());

            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new AsqPersistenciaException(e.getLocalizedMessage());
            }
        }




        @PostMapping("/remover")
        @ApiOperation(value = "Remove os dados da entidade Parametro")
        public void removerParametros(@RequestBody Integer idParametro, String usuario){

            try {

                this.parametroService.remover(idParametro);

                registraAuditoria(usuario, TipoOperacaoEnum.EXCLUIR.getNome());

            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new AsqPersistenciaException(e.getLocalizedMessage());
            }
        }



        @PostMapping("/salvar")
        @ApiOperation(value = "Inclui os dados da entidade Parametro")
        public void salvarParametros(@RequestBody Parametro parametro, String usuario){

            try {

                this.parametroService.salvar(parametro);

                registraAuditoria(usuario, TipoOperacaoEnum.SALVAR.getNome());

            } catch (Exception e) {
                log.error(e.getLocalizedMessage() + this.getClass().getName());
                throw new AsqPersistenciaException(e.getLocalizedMessage());
            }
        }




        @PostMapping("/consultarParametroPorCodigo")
        @ApiOperation(value = "Consuta os dados da entidade Parametro, por meio de sua chave primária")
        public Parametro consultarParametroPorCodigo(@RequestBody Parametro parametro) throws AsqPersistenciaException{

            try {

                return this.parametroService.consultarParametroPorCodigo(parametro);

            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new AsqPersistenciaException(e.getLocalizedMessage());
            }

        }

    private void registraAuditoria(String usuario, String operacao) {
        Auditoria auditoria = preencheAuditoria(usuario, operacao);
        this.auditoriaController.salvarAuditorias(auditoria);
    }

    private Auditoria preencheAuditoria(String usuario, String operacao) {
        Auditoria auditoria = new Auditoria();
        auditoria.setOperacao(operacao);
        auditoria.setDataCriacao(new Date());
        auditoria.setEntidade(Parametro.class.getSimpleName());
        auditoria.setUsuario(usuario);
        return auditoria;
    }

}

 */