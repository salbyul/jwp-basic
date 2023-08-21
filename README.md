#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 먼저 WebListener 어노테이션이 사용된 클래스의 초기화를 먼저 진행하게 된다.
* WebListener 어노테이션이 등록된 클래스는 ServletContextListener 인터페이스를 구현하여 초기화 메서드를 작성할 수 있다.
* 서버가 구동될 때 contextInitialized 메서드를 실행하여 초기화 작업을 진행한다.
* 현재 프로젝트에서 초기화 작업으로는 jwp.sql 스크립트를 읽어 DB에 적용한다.
* 현재 서블릿이 DispatcherServlet 하나뿐이므로 이 DispatcherServlet 하나만을 서블릿 컨테이너에 등록한다.
* 그 후 init() 메소드를 실행하여 DispatcherServlet의 초기화 작업을 진행한다.
* DispatcherServlet의 초기화 작업은 사용하는 URI에 맞는 Controller를 등록하여 Mapping 하게 된다.
* DispatcherServlet의 loadOnStart 의 값이 1이므로 서블릿 컨테이너에 등록되는 순간에 초기화 작업을 진행한다. (즉시 로딩)
* DispatcherServlet의 URLPatterns가 '/'이므로 모든 요청을 DispatcherServlet이 수행한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트가 http://localhost:8080 으로 요청을 하게 되면 WebSocket과 연결이 되며 새로운 쓰레드가 생성된다.
* 해당 WebSocket의 InputStream 으로 Http Request 프로토콜이 오게 되며 이를 분석해 HttpServletReqeust 객체를 생성한다.
* 그 후 요청 URI에 맞는 Servlet이 요청을 수행하게 된다.
* 현재 프로젝트에서는 DispatcherServlet이 모든 요청을 수행하게 된다.
* DispatcherServlet이 해당 URI('/')와 매핑된 HomeController 인스턴스의 execute 메서드를 호출한다.
* 그 후 DispatcherServlet으로 돌아오게 되면 execute 메서드의 반환값인 ModelAndView 를 받고 View의 render 메서드를 호출하여 응답을 하게 된다.
* 응답을 하는 과정은 HttpServletResponse를 분석해 WebSocket의 OutputStream 으로 Http Response 프로토콜을 전송하게 된다.
* 현재 프로젝트에서는 클라이언트가 이를 받게 되면 Html 파일이 Response body로 전송되게 되며 해당 Html이 필요로 하는 CSS, Javascript 파일을 재요청하게 된다.
* 이하 같은 과정을 거쳐 응답을 하게 된다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 멀티 쓰레드에서 문제가 될 수 있는 부분은 
``` java
private Question question;
private List<Answer> answers;
```
위의 필드이다.
* 가변 필드를 갖는 객체를 멀티 쓰레드 환경에서 이용하게 되면 해당 필드들의 값이 바뀔 수 있기 때문에 문제가 될 수 있다.
* 해당 문제를 해결하기 위해서는 가변 필드를 제거해야 한다.
* final 키워드를 붙여 불변 필드로 변경하거나 해당 필드를 지역 변수로 변경하여 사용하여야 한다.
