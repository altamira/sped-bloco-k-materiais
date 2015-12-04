package br.com.altamira.material;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import br.com.altamira.material.model.Material;
import br.com.altamira.material.model.MaterialInventario;
import br.com.altamira.material.model.MaterialInventarioMedida;
import br.com.altamira.material.model.MaterialMovimento;
import br.com.altamira.material.model.MaterialMovimentoItem;
import br.com.altamira.material.model.Medida;

@Configuration
public class SpringRESTConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Material.class, Medida.class, MaterialInventarioMedida.class, MaterialInventario.class, MaterialMovimento.class, MaterialMovimentoItem.class);
    }
}