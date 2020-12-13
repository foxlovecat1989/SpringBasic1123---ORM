package jdbc.invoice;

import com.mycompany.springbasic1123.invoice.entity.Item;
import java.util.List;
import org.junit.Test;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class Test2 {

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
    JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);

    @Test
    public void t2() {
        // SimpleFlatMapper
        ResultSetExtractor<List<Item>> mapper
                = JdbcTemplateMapperFactory
                        .newInstance()
                        .addKeys("id")
                        .newResultSetExtractor(Item.class);
        // 注意資料表名稱對應關係
        String sql = "SELECT im.ID, im.AMOUNT,\n"
                + "       inv.ID AS invoice_id, inv.INVDATE AS invoice_date, \n"
                + "       ip.id AS product_id, ip.TEXT AS product_text,\n"
                + "       ip.PRICE AS product_price, ip.INVENTORY AS product_inventory\n"
                + "FROM Item im, Invoice inv, ItemProduct ip\n"
                + "WHERE im.INVID=inv.ID AND im.IPID=ip.ID";
        List<Item> items = jdbcTemplate.query(sql, mapper);
        System.out.println(items);
    }
}
