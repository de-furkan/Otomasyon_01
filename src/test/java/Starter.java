import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Starter {


    /*

    Tamam, bu örnekleri oldukça basit tuttuk; örneğin, Strings kullanıcı adı ekstra işlemlerle geliştirilebilir.


    Umarım bu ders çok faydalı olmuştur.

    Kodu son bir kez gözden geçirelim ve onu nasıl daha sürdürülebilir, okunabilir ve dinamik hale getirdiğimizi görelim.


     */
    WebDriver driver = null;
    WebElement usernameEl = null;
    WebElement passwordEl = null;


    @BeforeMethod
    public void setup() {
        //> WebDriver hazir durumda
        driver = new ChromeDriver();

        //> pencere boyutunu maximize -> genisletiyoruz
        driver.manage().window().maximize();

        //> herokuapp sitesine yonlendiriyoruz
        driver.get("https://the-internet.herokuapp.com/login");

        //> kullanici adi ve sifre ogeleri -> referans
        usernameEl = driver.findElement(By.id("username"));
        passwordEl = driver.findElement(By.id("password"));
    }


    //> super, olusturduk test senaryomuzu -> DataProvider'ile AMA....
    //> kullanici / sifre verdigimiz parametreler -> aynı sirada olmali

    @Test(dataProvider = "kimlikVerileri")
    public void kimlikBilgileriKucukHarf(String kullanici, String sifre) throws InterruptedException {

        //> kullanıcı kimlik bilgisi verileri gonderiyoruz
        usernameEl.sendKeys(kullanici);
        passwordEl.sendKeys(sifre);

        //> Farki gormek icin programi biraz bekletelim
        Thread.sleep(2000); // 2000 = 2 saniye
    }


    //> Faker kullanalim
    //> oncellikle object olusturmamiz lazim bu sekilde
    //> kucuk harf ve buyuk harf (6 tane veri yapalim)
    //> 1. kullanici adi     2. parola/sifre
    //> DataProvider hazir durumda -> simdi nerde kullanacagiz?

    Faker faker = new Faker();

    @DataProvider(name = "kimlikVerileri")
    @Test
    public Object[][] kimlikVerileri() {

        return new Object[][] {

                //> kucuk harfler
                {faker.name().name().toLowerCase(), faker.country().name().toLowerCase()},
                {faker.name().name().toLowerCase(), faker.country().name().toLowerCase()},
                {faker.name().name().toLowerCase(), faker.country().name().toLowerCase()},

                //> simdi buyuk harfler
                {faker.name().name().toUpperCase(), faker.country().name().toUpperCase()},
                {faker.name().name().toUpperCase(), faker.country().name().toUpperCase()},
                {faker.name().name().toUpperCase(), faker.country().name().toUpperCase()}
        };
    }

    @AfterMethod
    public void tearDown() {
        //> tarayici kapat
        driver.quit();
    }

    /*
 Biz:
     - Kodu daha okunabilir hale getirdik (Test Case -- TC ismi daha iyi verilebilirdi)
     - DataProvider -> Veriye Dayalı Test ile de dinamik değerler oluşturduk
     - Ve @BeforeMethod ve @AfterMethod yöntemlerinden faydalandık :)
     - 'Tarayıcı oturumlarını buyutme' seçeneğini de unutmadık :)

     DRY prensibini takip ettik
     "Kendini tekrar etme"

     Tesekkurler :D
     @LinkedIn Furkan ✅
 */
}

