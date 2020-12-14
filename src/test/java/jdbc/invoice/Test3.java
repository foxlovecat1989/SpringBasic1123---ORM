package jdbc.invoice;


import com.mycompany.springbasic1123.jdbc.invoice.dao.InvoiceDao;
import com.mycompany.springbasic1123.jdbc.invoice.dao.InvoiceDaoImpl;
import com.mycompany.springbasic1123.jdbc.invoice.entity.Item;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jdbc-config.xml"})
public class Test3 {
   
    @Autowired
    private InvoiceDaoImpl invoiceDao;
    
    @Test
    public void t3() {
        List<Item> items = invoiceDao.queryItem();
        System.out.println(items);
    }
}
