package invoice;

import com.mycompany.springbasic1123.invoice.entity.Item;
import java.util.List;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Test1 {
    
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
    JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    
    @Test
    public void t1(){
        String sql = "SELECT * FROM Item";
        List<Item> items = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Item.class));
        System.out.println(items);
    }
}
