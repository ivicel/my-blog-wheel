package info.ivicel.blog;

import info.ivicel.blog.core.repository.PostRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WheelApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void contextLoads() {


    }
}
