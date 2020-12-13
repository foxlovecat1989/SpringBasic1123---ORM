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
-> [Item{id=1, amount=5, product=null, invoice=null}, .......

# 4. 複數資料表時:
-> 必須自訂 class implements RowMapper - override -> mapRow(ResultSet rs, int i) throws SQLException {

# 5. 問題: 當關聯資料表 關聯過多 怎麼辦?
-> SimpleFlatMapper





