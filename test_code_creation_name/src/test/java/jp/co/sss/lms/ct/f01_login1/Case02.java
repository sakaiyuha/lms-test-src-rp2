package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		//URL
		goTo("http://localhost:8080/lms/");

		String pageTitle = webDriver.getTitle();

		assertEquals("ログイン | LMS", pageTitle);

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		//ログインID
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		//パスワード
		WebElement password = webDriver.findElement(By.name("password"));

		loginId.clear();
		password.clear();

		//DBに登録されていない情報
		loginId.sendKeys("Testtest01");
		password.sendKeys("Testtest01");

		password.sendKeys(Keys.ENTER);

		WebElement error = webDriver.findElement(By.className("error"));

		assertEquals("* ログインに失敗しました。", error.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
