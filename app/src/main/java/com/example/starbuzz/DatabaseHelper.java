package com.example.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLInput;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "starbuzz.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ALAMAT TEXT," +
                "EMAIL TEXT, PHONE TEXT, PIN TEXT,TOTALORDER INTEGER, STATUS INTEGER)");
        db.execSQL("CREATE TABLE PAYMENT(PAYMENTID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,PAYMENTGAMBAR INTEGER)");
        db.execSQL("CREATE TABLE STORES (STOREID INTEGER PRIMARY KEY AUTOINCREMENT, NAMALOKASI TEXT," +
                "JAMBUKA TEXT,JAMTUTUP TEXT,ALAMAT TEXT)");
        db.execSQL("CREATE TABLE KATEGORI (KATEGORIID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, DESKRIPSI TEXT, KATEGORIGAMBAR INTEGER," +
                "KATEGORIICON INTEGER)");
        db.execSQL("CREATE TABLE MENU (MENUID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, MENUGAMBAR INTEGER,DESKRIPSI TEXT,HARGA INTEGER, KATEGORIID INTEGER," +
                "FAVORITES INTEGER, FOREIGN KEY (KATEGORIID) REFERENCES KATEGORI (KATEGORIID))");
        db.execSQL("CREATE TABLE PEMESANAN(PEMESANANID INTEGER PRIMARY KEY AUTOINCREMENT, TANGGAL DATE,PAJAK INTEGER," +
                "ONGKIR INTEGER, TOTAL INTEGER, PAYMENTID INTEGER, STOREID INTEGER, USERID INTEGER," +
                "FOREIGN KEY (PAYMENTID) REFERENCES PAYMENT(PAYMENTID), FOREIGN KEY (STOREID) REFERENCES STORES(STOREID)," +
                "FOREIGN KEY(USERID) REFERENCES USERS (USERID))");
        db.execSQL("CREATE TABLE DETPEMESANAN (MENUID INTEGER,PEMESANANID INTEGER, JUMLAH INTEGER, " +
                "SUBTOTAL INTEGER," +
                "PRIMARY KEY (MENUID,PEMESANANID)," +
                "FOREIGN KEY (MENUID) REFERENCES MENU(MENUID)," +
                "FOREIGN KEY (PEMESANANID) REFERENCES PEMESANAN (PEMESANANID))");

        //INSERT KATEGORI
        insertKATEGORI(db,"Drinks","Your beloved coffee made with love",R.drawable.kategoridrinks,R.drawable.drinksicon);
        insertKATEGORI(db,"Foods","Delicious foods make your day",R.drawable.kategorifoods,R.drawable.foodsicon);
        insertKATEGORI(db,"Whole Beans","Make your own coffee with best quality beans",R.drawable.kategoribeans,R.drawable.beansicon);
        insertKATEGORI(db,"Tumbler","Own our unique collection of tumbler",R.drawable.kategoritumbler,R.drawable.tumblericon);
        //END INSERT KATEGORI

        //INSERT MENU
        //DRINKS
        insertMENU(db,"Espresso","Our smooth signature Espresso Roast with rich flavor and caramelly sweetness is at the very heart of everything we do.",
                R.drawable.espresso,1,47000,0);
        insertMENU(db,"Almondmilk Honey Flat White","This flat white intentionally pairs almondmilk and signature espresso with a hint of honey, making a perfect amount of creamy, nutty sweetness.",
                R.drawable.almondmilk,1,60000,0);
        insertMENU(db,"Oatmilk Honey Latte","Oatmilk and signature espresso are intentionally combined with a hint of honey and a toasted honey topping—a savory tribute to all things sweet and salty.",
                R.drawable.oatmilk,1,45000,0);
        insertMENU(db,"Cocoa Cloud Macchiato* (Contains eggs)","Our Cloud Macchiato: light and airy with layers of fluffy foam*, cascading espresso, flavored toffee nut syrup, our signature caramel crosshatch and a mocha drizzle swirl. A take on a whole new way to love your macchiato.",
                R.drawable.cocoacloud,1,42000,0);
        insertMENU(db,"White Chocolate Mocha","Our signature espresso meets white chocolate sauce and steamed milk, and then is finished off with sweetened whipped cream to create this supreme white chocolate delight.",
                R.drawable.white,1,50000,0);
        insertMENU(db,"Almondmilk Honey Flat White","This flat white intentionally pairs almondmilk and signature espresso with a hint of honey, making a perfect amount of creamy, nutty sweetness.",
                R.drawable.honeyflat,1,53000,0);
        insertMENU(db,"Smoked Butterscotch Crème","Notes of butterscotch and subtle smokiness intermingle with a dash of spice. Meet your new treat-yourself beverage.",
                R.drawable.smoked,1,35000,0);
        insertMENU(db,"Steamed Milk","Enjoy a warm cup of skim, 2%, soy, almond or coconutmilk steamed for your sipping pleasure.",
                R.drawable.milk,1,32000,0);
        insertMENU(db,"Caramel Apple Spice","Steamed apple juice complemented with cinnamon syrup, whipped cream and a caramel sauce drizzle.",
                R.drawable.applespice,1,38000,0);
        insertMENU(db,"Mocha Cookie Crumble Frappuccino® Blended Beverage","Frappuccino® Roast coffee, mocha sauce and Frappuccino® chips blended with milk and ice, layered on top of whipped cream and chocolate cookie crumble and topped with vanilla whipped cream, mocha drizzle and even more chocolate cookie crumble. Each sip is as good as the last . . . all the way to the end.",
                R.drawable.cookie,1,58000,0);
        //END DRINKS
        //FOODS
        insertMENU(db,"Crispy Grilled Cheese Sandwich","A delicious blend of white Cheddar and mozzarella cheeses on sourdough bread, topped with a Parmesan butter spread.",
                R.drawable.crispy,2,32000,0);
        insertMENU(db,"Turkey & Basil Pesto","Thick-sliced turkey and melted provolone cheese stacked on a ciabatta roll, then topped with our signature basil pesto and dry-roasted red peppers. So tasty, you'll want seconds, but so satisfying, you won't need them. Turkeys are raised without the use of antibiotics.",
                R.drawable.turkey,2,32000,0);
        insertMENU(db,"Cheese & Fruit Protein Box","Brie, Gouda, two-year aged Cheddar cheeses, nine-grain crackers, apples and grapes make this the perfectly balanced box to enjoy any time of day. Now with more cheese, more fruit and 20 g protein.\n",
                R.drawable.cheese,2,40000,0);
        insertMENU(db,"Chicken Wrap Protein Box","This delicious meal—made with grilled white chicken, peanut-coconut sauce, chile-lime slaw and ginger cream cheese in a chile tortilla—is a flavor-packed wrap with even more protein as well as additional cucumbers and apple slices on the side.. Now with 30 g of protein.",
                R.drawable.chicken,2,78000,0);
        insertMENU(db,"Dipped Madeleines","Rich, buttery and moist with lightly crisped edges to create a delicious and soft madeleine dipped in chocolaty coating.",
                R.drawable.madeleine,2,41000,0);
        //END FOODS
        //BEANS
        insertMENU(db,"Sun-Dried Papua New Guinea Ulya Starbucks Reserve®","Notes of baked pineapple and brown sugar with a syrupy mouthfeel and hints of eucalyptus. In this remote place of otherworldly beauty, fortune favors the bold. The result is our first sun-dried coffee from Papua New Guinea, and it's a triumph.",
                R.drawable.papua,3,96000,0);
        insertMENU(db,"Veranda Blend®","In Latin America, coffee farms are often run by families, with their own homes on the same land where their coffee grows. We’ve sipped coffee with these farmers for decades, sitting on their verandas, overlooking the lush beauty of the coffee trees rolling out in the distance. Most times it was a lightly roasted coffee like this one. It took us more than 80 tries to get it right—mellow and flavorful with a nice softness.",
                R.drawable.veranda,3,87000,0);
        insertMENU(db,"Passport Series: West Java","The word \"Java\" conjures the lore and romance of coffee. The story began in the 17th century, when seedings first arrived on the island from the port of Mocha. With densley forested volcanic mountains, wild coffee gardens and a tropical climate, the region is a paradise for cultivating remarkable beans. Processed using a semi-washed method that has a rich history in the region, this coffee mesmerizes. In the cup, aromas of sage are layered with flavors of bittersweet chocolate and vanilla-like sweetness.",
                R.drawable.westjava,3,100000,0);
        insertMENU(db,"Decaf Sumatra","Full bodied decaf dark roast coffee with a smooth mouthfeel and lingering herbal notes.",
                R.drawable.sumatra,3,92000,0);
        insertMENU(db,"Komodo Dragon Blend®","Deep, untamed flavors ripple in this cup—earthy, herbal and complex with a lingering spice. This is the essence of coffees from Indonesia. Our master coffee blenders have brought together the best flavors of the region perfectly, creating an experience that would otherwise never exist. And is as extraordinary and wild as its namesake.",
                R.drawable.komodo,3,105000,0);
        //END BEANS
        //TUMBLER
        insertMENU(db,"Premium Everki Tumbler","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                R.drawable.tumbler1,4,80000,0);
        insertMENU(db,"Premium Coffee Tumbler","Your is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                R.drawable.tumbler2,4,74000,0);
        insertMENU(db,"Majestic Red Tumbler","There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.",
                R.drawable.tumbler3,4,74000,0);
        //END TUMBLER
        //END INSERT MENU

        //INSERT STORES
        insertSTORES(db,"UNIVERSITAS TARUMANAGARA","09:00","20:00","Universitas Tarumanagara, Letjen S. Parman St, RT.6/RW.16, Tomang, Grogol petamburan, West Jakarta City, Jakarta 11440");
        insertSTORES(db, "PRJ","10:00","22:00",
                " ITC Cempaka Mas, Jl. Letnan Jend. R. Suprapto, Kemayoran No.9, RW.8, Sumur Batu, Kec. Kemayoran, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10640");
        insertSTORES(db,"GOLDEN GN SAHARI","09:00","22:00",
                "Mal Golden Truly, Jl. Gn. Sahari No.59, RT.2/RW.1, Gn. Sahari Sel., Kec. Kemayoran, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10610");
        insertSTORES(db,"MANGGA DUA MALL","10:00","23:00"," Jl. Arteri Mangga Dua, Lantai 1, RT.1/RW.12, Mangga Dua Sel., Jakarta Pusat, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10730");
        insertSTORES(db,"GRAND INDONESIA","09:00","21:00","Grand Indonesia East Mall Lt. 3, Jl. M.H. Thamrin No. 1, Menteng, RT.1/RW.5, Menteng, Kec. Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10310");
        insertSTORES(db,"PURI INDAH MALL","08:00","23:00","Jl. Puri Agung, RT.1/RW.2, South Kembangan, Kembangan, West Jakarta City, Jakarta 11610");
        insertSTORES(db,"HAYAM WURUK","09:00","23:00","Hayam Wuruk Plaza, Jl. Hayam Wuruk No.108, RT.4/RW.9, Maphar, Kec. Taman Sari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11160");
        insertSTORES(db,"SKYLINE BUILDING","10:00","21:00","Skyline Building, Jl. M.H. Thamrin No.9, RT.2/RW.1, Kb. Sirih, Kec. Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10230");
        insertSTORES(db,"PLAZA CENTRAL","09:00","21:00","Jl. Jend. Sudirman No.47, RT.5/RW.4, Karet Semanggi, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12930");
        insertSTORES(db,"CENTRAL PARK","08:00","22:00","No.Kav 28, Jalan Let Jend S Parman, RT.12/RW.6, Tj. Duren Sel., Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470");
        //END INSERT STORES

        //insertPAYMENT
        insertPAYMENT(db,"OVO",R.drawable.ovo);
        insertPAYMENT(db,"GoPay",R.drawable.gopay);
        insertPAYMENT(db,"BCA",R.drawable.bca);
        //END insertPAYMENT

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }

    //START INSERT
    public boolean insertUSERS(String name, String alamat,String email,String phone, String PIN, Integer totalorder, Integer status ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("ALAMAT",alamat);
        contentValues.put("EMAIL",email);
        contentValues.put("PHONE",phone);
        contentValues.put("PIN",PIN);
        contentValues.put("TOTALORDER",totalorder);
        contentValues.put("STATUS",status);
        long result =db.insert("USERS",null,contentValues);
        if(result==-1){
            return false;
        }else return true;
    }

    public boolean insertPAYMENT(SQLiteDatabase db,String name, Integer paymentgambar){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("PAYMENTGAMBAR",paymentgambar);
        long result = db.insert("PAYMENT",null,contentValues);
        if(result==-1){
            return false;
        }else return true;
    }

    public boolean insertSTORES(SQLiteDatabase db, String namalokasi, String jambuka, String jamtutup, String alamat){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMALOKASI",namalokasi);
        contentValues.put("JAMBUKA",jambuka);
        contentValues.put("JAMTUTUP",jamtutup);
        contentValues.put("ALAMAT",alamat);
        long result = db.insert("STORES",null,contentValues);
        if(result==-1)return true;
        else return false;
    }

    public boolean insertKATEGORI(SQLiteDatabase db, String name, String deskripsi, Integer kategorigambar, Integer kategoriicon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("DESKRIPSI",deskripsi);
        contentValues.put("KATEGORIGAMBAR",kategorigambar);
        contentValues.put("KATEGORIICON", kategoriicon);
        long result = db.insert("KATEGORI",null,contentValues);
        if(result==-1)return true;
        else return false;
    }

    public boolean insertMENU(SQLiteDatabase db, String name,String deskripsi, Integer menugambar,Integer kategoriid, Integer harga,Integer fav){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("MENUGAMBAR",menugambar);
        contentValues.put("DESKRIPSI",deskripsi);
        contentValues.put("KATEGORIID",kategoriid);
        contentValues.put("HARGA",harga);
        contentValues.put("FAVORITES",fav);
        long result = db.insert("MENU",null,contentValues);
        if(result==-1)return true;
        else return false;
    }

    public boolean insertPEMESANAN(Date tanggal, Integer pajak, Integer ongkir, Integer total,
                                   Integer paymentid, Integer storeid, Integer userid ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TANGGAL", String.valueOf(tanggal));
        contentValues.put("PAJAK",pajak);
        contentValues.put("ONGKIR",ongkir);
        contentValues.put("TOTAL",total);
        contentValues.put("PAYMENTID",paymentid);
        contentValues.put("STOREID",storeid);
        contentValues.put("USERID",userid);
        long result = db.insert("PEMESANAN",null,contentValues);
        if(result==-1)return true;
        else return false;
    }

    public boolean insertDETPEMESANAN(String menuid, String pemesananid,Integer jumlah,
                                      Integer subtotal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MENUID", menuid);
        contentValues.put("PEMESANANID",pemesananid);
        contentValues.put("JUMLAH",jumlah);
        contentValues.put("SUBTOTAL",subtotal);
        long result = db.insert("DETPEMESANAN",null,contentValues);
        if(result==-1)return true;
        else return false;
    }

    //END INSERT

    //SELECT
    public Cursor getUSERSifLOGIN(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res =db.rawQuery("SELECT*FROM USERS WHERE status = 1",null);
        return res;
    }
    public Cursor getUSERSbyPHONE(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM USERS WHERE PHONE = ?", new String[] {phone});
        return res;
    }
    public Cursor getAllCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("SELECT*FROM KATEGORI",null);
        return res;
    }

    public Cursor getMenuByCategory(String kategoriid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU WHERE kategoriid = ?",new String[]{kategoriid});
        return res;
    }

    public Cursor getMenuByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU WHERE MENUID = ?",new String[]{id});
        return res;
    }

    public Cursor getFAVORITES(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU WHERE FAVORITES = 1",null);
        return res;
    }

    public Cursor menuByNAME(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU WHERE NAME= ?", new String[]{name});
        return res;
    }

    public Cursor getKATEGORIIDbyNAME(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM KATEGORI WHERE NAME= ?", new String[]{name});
        return res;
    }

    public Cursor getKATEGORINAMEbyID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM KATEGORI WHERE KATEGORIID= ?", new String[]{id});
        return res;
    }

    public Cursor getMENUbyNAME(String nma){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU WHERE NAME= ?", new String[]{nma});
        return res;
    }
    public Cursor getOUTLET(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM STORES",null);
        return res;
    }

    public Cursor getALLmenu(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM MENU",null);
        return res;
    }

    public Cursor getLASTorder(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM PEMESANAN WHERE PEMESANANID =" +
                "(SELECT (MAX (PEMESANANID)) FROM PEMESANAN)",null);
        return res;
    }
    public Cursor getDETPEMESANANByIDS(String orderid,String menuid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM DETPEMESANAN WHERE PEMESANANID = ? AND MENUID = ?",
                new String[]{orderid,menuid});
        return res;
    }

    public Cursor getPEMESANANDET(String pemesananid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM DETPEMESANAN WHERE PEMESANANID = ?",
                new String[]{pemesananid});
        return res;
    }

    public Cursor getSubtotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM (SUBTOTAL) AS SUM FROM DETPEMESANAN " +
                        "WHERE PEMESANANID = (SELECT MAX(PEMESANANID) FROM PEMESANAN) ",
                null);
        return res;
    }

    public Cursor getLASTDETPEMESANAN(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM detpemesanan WHERE pemesananid = (SELECT MAX(PEMESANANID) FROM PEMESANAN)",null);
        return res;
    }

    public Cursor getUserHistory(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM PEMESANAN WHERE USERID = ? AND " +
                "TOTAL != 0",new String[]{userid});
        return res;
    }

    public Cursor getSTOREnamebyID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT*FROM STORES WHERE STOREID = ? ",new String[]{id});
        return res;
    }

    public Cursor getUSERTOTALORDER(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM PEMESANAN WHERE USERID = ? " +
                "AND TOTAL!=0 ",new String[]{userid});
        return res;
    }
    //END SELECT

    //UPDATE
    public boolean updateUSERS(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        db.update("USERS",contentValues,"USERID = ?",new String[]{id});
        return true;
    }

    public boolean userLOGOUT(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS",0);
        db.update("USERS",contentValues,"USERID = ?",new String[]{id});
        return true;
    }

    public boolean userLOGIN(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS",1);
        db.update("USERS",contentValues,"USERID = ?",new String[]{id});
        return true;
    }

    public boolean menuFAVORITES(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVORITES",1);
        db.update("MENU",contentValues,"MENUID = ?",new String[]{id});
        return true;
    }
    public boolean menuREMOVEfavorites (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVORITES",0);
        db.update("MENU",contentValues,"MENUID = ?",new String[]{id});
        return true;
    }

    public boolean updateDETPEMESANAN(String orderid, String menuid,Integer jumlah, Integer subtotal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("JUMLAH",jumlah);
        contentValues.put("SUBTOTAL",subtotal);
        db.update("DETPEMESANAN",contentValues,"MENUID = ? AND PEMESANANID = ?", new String[]{menuid,orderid});
        return true;
    }

    public boolean updatePEMESANAN(String orderid, Integer ongkir,Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ONGKIR",ongkir);
        contentValues.put("TOTAL",total);
        db.update("PEMESANAN",contentValues,"PEMESANANID = ?", new String[]{orderid});
        return true;
    }
    //END UPDATE

    //DELETE
    public Integer deleteDETPEMESANANbyIDS(String orderid,String menuid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("DETPEMESANAN","MENUID = ? AND PEMESANANID = ?",new String[]{menuid,orderid});
    }
    //END DELETE
}

