package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.Set;

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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ログインID
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		//パスワード
		WebElement password = webDriver.findElement(By.name("password"));

		loginId.clear();
		password.clear();

		//DBに登録されている情報
		loginId.sendKeys("StudentAA01");
		password.sendKeys("Testtest01");

		password.sendKeys(Keys.ENTER);

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		WebElement dropdown = webDriver.findElement(By.linkText("機能"));
		dropdown.click();
		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		String pageTitle = webDriver.getTitle();

		assertEquals("ヘルプ | LMS", pageTitle);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		WebElement Question = webDriver.findElement(By.linkText("よくある質問"));
		Question.click();

		String nowWindowHandle = WebDriverUtils.webDriver.getWindowHandle();

		Set<String> windowHandles = WebDriverUtils.webDriver.getWindowHandles();

		for (String handle : windowHandles) {
			if (!handle.equals(nowWindowHandle)) {
				WebDriverUtils.webDriver.switchTo().window(handle);
				break;
			}
		}
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//キーワード
		WebElement keyword = webDriver.findElement(By.name("keyword"));

		keyword.sendKeys("研修");

		keyword.sendKeys(Keys.ENTER);

		pageLoadTimeout(5);

		WebElement result = webDriver.findElement(By.className("mb10"));
		assertEquals("Q.助成金書類の作成方法が分かりません", result.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		WebElement clear = webDriver.findElement(By.xpath("//input[@value='クリア']"));

		clear.click();

		pageLoadTimeout(5);

		WebElement keyword = webDriver.findElement(By.name("keyword"));

		assertEquals("", keyword.getText());

		getEvidence(new Object() {
		});

	}

}
