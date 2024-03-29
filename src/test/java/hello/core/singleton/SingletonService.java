package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
        // 생성자를 private으로 만들어 외부에서 생성자를 호출하지 못하도록 만든다.
    }
    
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
