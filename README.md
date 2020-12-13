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





