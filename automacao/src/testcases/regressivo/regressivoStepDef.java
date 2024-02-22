package regressivo;

import java.util.Properties;

import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class regressivoStepDef {

    RequestSpecification request;
    Response response = null;
    String SERVICE_BaseUrl;
    String environment;

    @Given("^que eu quero executar um servico no ambiente$")
    public void que_eu_quero_executar_um_servico_no_ambiente() throws Throwable {

        environment = utilitarios.funcoes.ReturnEnvironment();

        Properties prop = utilitarios.funcoes.getprop();

        SERVICE_BaseUrl = prop.getProperty("prop.server.host." + environment);

    }

    @When("^envio a solicitacao GET$")
    public void envio_a_solicitacao_GET() throws Throwable {

        request = RestAssured.given();
        response = request.when().relaxedHTTPSValidation().get("https://bff-kit-springboot" + SERVICE_BaseUrl + "/health");

    }

    @Then("^valido o codigo de status \"([^\"]*)\"$")
    public void valido_o_codigo_de_status(String StatusCode) throws Throwable {

        System.out.println(response.getStatusCode());
        Assert.assertEquals(StatusCode, String.valueOf(response.getStatusCode()));

    }
}
