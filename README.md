# SpringBasic1123---ORM
# <dependency> 
<!-- SimpleFlatMapper --> 
        <dependency> 
            <groupId>org.simpleflatmapper</groupId> 
            <artifactId>sfm-springjdbc</artifactId> 
            <version>3.11.7</version> 
        </dependency>
        
# 1. 說明 關聯關係
# 2. 建置 Entity -> class 對應 資料表


# --- Entity對應資料表 原理 ---
# 3. RowMapper 只能對應單一資料表
result -> [Item{id=1, amount=5, product=null, invoice=null}, .......

# 4. 複數資料表時:
-> 必須自訂 class implements RowMapper - override -> mapRow(ResultSet rs, int i) throws SQLException {
result -> [Item{id=1, amount=5, product=ItemProduct{id=1, text=Pen, price=10, inventory=20}, invoice=Invoice{id=1, date=null}},

# 5. 問題: 當關聯資料表 關聯過多 怎麼辦?
-> 使用SimpleFlatMapper
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
        
# 6. 練習 SQL
--每一張發票有那些商品?
SELECT inv.ID, ip.TEXT 
FROM INVOICE as inv, ITEMPRODUCT as ip, ITEM as im
WHERE inv.ID = im.INVID AND im.IPID = ip.ID;

--每一張發票有幾項商品?
SELECT inv.ID, count(ip.TEXT) as amount
FROM INVOICE as inv, ITEMPRODUCT as ip, ITEM as im
WHERE inv.ID = im.INVID AND im.IPID = ip.ID
GROUP BY inv.ID;

--每一張發票價值多少?
SELECT inv.ID, SUM(ip.PRICE * im.AMOUNT) as subtotal
FROM INVOICE as inv, ITEMPRODUCT as ip, ITEM as im
WHERE inv.ID = im.INVID AND im.IPID = ip.ID
GROUP BY inv.ID;

--每一樣商品各賣了多少?
SELECT ip.TEXT, SUM(ip.PRICE * im.AMOUNT) as subtotal
FROM Item as im, ITEMPRODUCT as ip
WHERE im.IPID = ip.ID
GROUP BY ip.TEXT;

--哪一件商品賣得錢最多?
SELECT ip.TEXT, SUM(ip.PRICE * im.AMOUNT) as subtotal
FROM Item as im, ITEMPRODUCT as ip
WHERE im.IPID = ip.ID
GROUP BY ip.TEXT
ORDER BY subtotal DESC
FETCH FIRST 1 ROWS ONLY

# 7. 練習 Java8 stream & groupingBy
 // 每一張發票有那些商品?
        // 使用 Java 8 grouping by
        System.out.println("每一張發票有那些商品?");
        System.out.println(
                items
                    .stream()
                    .collect(groupingBy(item -> item.getInvoice().getId(), Collectors.toList()))
        );
        
        //每一張發票有幾件商品?
        System.out.println("每一張發票有幾件商品?");
        System.out.println(
                items
                    .stream()
                    .collect(groupingBy(item -> item.getInvoice().getId(), Collectors.counting()))
        
        );
        
        //每一樣商品各賣了多少?
        System.out.println("每一樣商品各賣了多少?");
        
               Map<String, Integer> map = 
                    items.stream()
                    .collect(groupingBy(item -> item.getProduct().getText(), 
                            Collectors.summingInt( item -> item.getAmount() * item.getProduct().getPrice())));
      
        System.out.println(map);
        //哪一件商品賣得錢最多?
        System.out.println("哪一件商品賣得錢最多?");
        String max = map.entrySet()
                                .stream()
                                .max(Comparator.comparing(entry -> entry.getValue()))
                                .get()
                                .getKey();
        System.out.println(max);
        
        //每一張發票價值多少?
        System.out.println("每一張發票價值多少?");
        Map<Integer, Integer> map2 = 
                        items.stream()
                             .collect(groupingBy(item -> item.getInvoice().getId(), Collectors.summingInt(item -> item.getAmount() * item.getProduct().getPrice())));
        System.out.println(map2);
        
        //哪一張發票價值最高?
        System.out.println("哪一張發票價值最高?"); 
        Integer maxInvoice = 
                    map2.entrySet().stream()
                            .max(Comparator.comparing(entry -> entry.getValue() ))
                            .get()
                            .getKey();
        System.out.println(maxInvoice);




