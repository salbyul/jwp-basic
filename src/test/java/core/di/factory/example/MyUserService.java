package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyUserService {

    @Inject
    private QuestionRepository questionRepository;
    @Inject
    private UserRepository userRepository;

    public void setQuestionRepository(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
