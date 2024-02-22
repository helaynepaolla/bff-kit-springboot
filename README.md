# bff-kit-springboot - Artefato Kit de BFF - Backend for Frontend em Java e Spring Boot :rocket:

<br /><br />
![Java](https://img.shields.io/badge/Java-17-green?style=plastic&logo=java)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2.7.5-green?style=plastic&logo=spring)
![Spring Cloud](https://img.shields.io/badge/SpringCloud-2021.0.3-green?style=plastic&logo=spring)
![JUnit](https://img.shields.io/badge/JUnit-5-green?style=plastic&)
![Maven](https://img.shields.io/badge/Maven-green?style=plastic)

O **bff-kit-springboot** é um Kit de BFF em `Java` e `Spring Boot` para ser utilizado em Cloud Pública e serve como
modelo de artefato atualizado e apoia no entendimento geral.
<br />

### ÍNDICE

1. [ Principais caracterísitas e responsabilidades deste artefato ](#principaisCaracteristicasResponsabilidade)
2. [ Pré-requisitos para desenvolvimento ](#preRequisitosDesenvolvimento)
3. [ Swagger ](#swagger)
4. [ Testes ](#testes)
5. [ Build ](#build)
6. [ Execução Local ](#execucaoLocal)
7. [ Configuração Spring Actuator ](#springActuator)
8. [ Precisa de mais informações? ](#maisInformacoes)
   <br /><br />

<a name="principaisCaracteristicasResponsabilidade"></a>

## Principais características e responsabilidades deste artefato

- Atua como intermediador para
  o [fed-kit-angular](https://bitbucket.bradesco.com.br:8443/projects/ENGCL/repos/fed-kit-angular/browse)
- Consome o [srv-kit-springboot](https://bitbucket.bradesco.com.br:8443/projects/ENGCL/repos/srv-kit-springboot/browse)
  para obter informações de entidades externas: Base de Dados, Mainframe, Cache Redis, etc
  <br /><br />
- Envio de status de execução da pipeline via MS Teams para o grupo e canal configurado em `pipeline-notifications.json`

<a name="preRequisitosDesenvolvimento"></a>

### Pré-requisitos para desenvolvimento

- Possuir acesso no [repositório NEXUS Bradesco](https://nexusrepository.bradesco.com.br:8443/)

- Possuir o [git](https://git-scm.com/) devidamente instalado e configurado

- Possuir o [Apache Maven](https://maven.apache.org/download.cgi) devidamente instalado e configurado. Caso não o tenha,
  fique tranquilo pois aqui ele é baixado e configurado via linha de comando.

- Possuir instalado Java Development Kit (JDK) - versão mínima 11. Pode ser obtido
  através do [site da Oracle](https://www.oracle.com/java/technologies/javase-downloads.html)

- Possuir uma IDE da sua preferência: [Intellij IDEA](https://www.jetbrains.com/idea/)
  , [Eclipse IDE](https://www.eclipse.org/ide/) ou [VSCode](https://code.visualstudio.com/download)

- [OPCIONAL] Possuir um container na sua estação (Docker Desktop/Rancher Desktop/Podman/containerd/etc) caso deseja
  executar o artefato em container.
  <br /><br />

<a name="swagger"></a>

### Swagger

- Link da interface gráfica da aplicação para o Swagger UI
    - [Executando Localmente - Profile LOCAL](http://localhost:8088/bff-kit-springboot/swagger-ui/index.html)

- Por questões de segurança, o Swagger está devidamente configurado para estar desabilitado em ambiente produtivo - PRD

- Para utilização em outro ambiente deverá utilizar o IP do servidor: `http://IP_SERVIDOR:8080/swagger-ui/index.html`
  <br /><br />

<a name="testes"></a>

### Testes

- Construção dos Testes Unitários seguindo a prática
  do `TDD - Test Driven Development` para garantir automaticamente a cobertura de testes
  exigida.

- Foco principal dos testes NÃO é o de criar os mesmos com o objetivo único de cobrir o percentual dos testes de
  cobertura. Os testes têm
  objetivo de: encontrar e corrigir bugs, evitar regressão indevida e testar de fato o que foi
  codificado/produzido.

- **Plugin Maven Surefure:** projetado para testes unitarios. Detecta os testes unitários pelos seguintes padrões:

```
**/Test*.java
**/*Test.java
**/*Tests.java
**/*TestCase.java
```

- **Plugin Maven Failsafe:** projetado para testes de integração. Detecta os testes de integração pelos seguintes
  padrões:

```
**/IT*.java
**/*IT.java
**/*ITCase.java
```

- Para mais detalhes sobre testes,
  acesse [The Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html).
  <br />

- Além destes, dentro do diretório `postman/collections` há um collection do Postman com testes práticos para testes
  simples nas APIs expostas no kit. Para testar as APIs do BFF, é necessário ter o kit de SRV também funcional (ou então
  criar um mock para o BFF e alterar o application-LOCAL.yaml)

#### Testes Unitários

- Teste de unidade é a fase do teste de software em que os módulos são testados individualmente.

- É sempre importante executar os testes unitários localmente e garantir que todos estejam passando **antes** de
  realizar
  um *pull request* para a branch dev e/ou release/esteira.

- Os testes estão localizados no diretório `src/test`

- Para executar os testes, realize o comando `mvn test` via [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)

- Saiba que também é possível executar os testes unitários através do [jacoco](https://www.baeldung.com/jacoco) para que
  ele apresente um status dos testes e de cobertura
  dos testes. Deste modo, evita qualquer surpresa antes de chegar ao SonarQube!
  <br /><br />

<a name="build"></a>

### Build

**Ambiente baseado em Unix - Caso não tenha o Maven, execute o comando abaixo para permitir que seja instalado e
configurado na sua estação**

```
./mvnw clean install
```

**Ambiente baseado em Windows - Caso não tenha o Maven, execute o comando abaixo para permitir que seja instalado e
configurado na sua estação**

```
./mvnw.cmd clean install
```

<br />

Execute `mvn clean install` para fazer o build o projeto. Serão armazenados no diretório `target` dentro da pasta do
projeto

```
mvn clean install
```

<a name="execucaoLocal"></a>

### Execução Local

#### Execução local - Profile LOCAL

- O profile LOCAL é indicado para execução na estação de desenvolvimento
- Deve ser ativado o profile LOCAL
- Irá atender sob o HOST http://localhost:8088/bff-kit-springboot/health

```
export SPRING_PROFILES_ACTIVE=LOCAL
java -jar target/bff-kit-springboot.jar

```

#### Execução localmente - Profile DEV

- O profile DEV é indicado para execução na estação de desenvolvimento obtendo e consumindo recursos do ambiente DEV
- Deve ser ativado o profile DEV
- Irá atender sob o HOST http://localhost:8080/health

```
export SPRING_PROFILES_ACTIVE=DEV
java -jar target/bff-kit-springboot.jar

```

#### Executando com aplicação em container local

* Verificar se a imagem com o mesmo nome da sua aplicação já existe:

```
docker images
```

* Se a imagem já existir, delete-a ou mude o nome

```
  docker rmi <NOME_DA_IMAGEM_DOCKER>
```

* Gerar a imagem Docker com a aplicação (deve-se executar build antes)
* Antes, navegue até o diretório docker. Precisará do arquivo Dockerfile

```
docker build -t bff-kit-springboot .
```

* Iniciando o container a partir do nome da imagem

```
docker run -dp 8088:3000 bff-kit-springboot
```

<br />

#### Execução localmente em modo Debug

- Para executar em modo debug com a IDE
- A aplicação vai realizar bind na porta 5005:

```

java  -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=5005,suspend=y -jar target/bff-kit-springboot.jar

```

- Vá no IntelliJ Run > Edit configurations... > Clique '+' > Remote JVM Debug
- Preencha os campos e após, clique em Ok/Apply.
    - Name: AppRemote
    - Host: localhost
    - Port: 5005
- Por fim, ainda no IntelliJ, Run > Debug 'AppRemote'.
  <br /><br />

<a name="springActuator"></a>

### Configuração Spring Actuator

- Está configurado para atender APENAS o /health por questões de segurança
- Não expõs dados de disco (diskSpace)
- Está configurado para ser utilizado
  pelos [probes liveness e readiness - Kubernetes](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/)
- Se executar com o profile LOCAL, irá atender sob HOST http://localhost:8080/health
  <br /><br />

<a name="maisInformacoes"></a>

### Precisa de mais informações?

- Link
  para [COE | Cloud > LEAP > Leap - Arquitetura de referência](https://confluence.bradesco.com.br:8443/pages/viewpage.action?pageId=136730699)

- Link
  para [CORPORATIVO | Programa Leap > bradesCode > Treinamento Cloud Pública Bradesco](https://confluence.bradesco.com.br:8443/pages/viewpage.action?pageId=244976149)
