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
* 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
