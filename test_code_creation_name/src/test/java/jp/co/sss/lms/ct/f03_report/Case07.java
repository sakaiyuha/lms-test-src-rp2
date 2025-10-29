package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		loginId.sendKeys("StudentAA02");
		password.sendKeys("Testtest02");

		password.sendKeys(Keys.ENTER);

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		WebElement table = webDriver.findElement(By.className("sctionList"));
		WebElement notsubmitted = table.findElement(By.xpath("//span[text()='未提出']"));

		WebElement detail = notsubmitted.findElement(By.xpath("//input[@value='詳細']"));
		detail.click();

		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		WebElement dailyReport = webDriver.findElement(By.xpath("//input[@value='日報【デモ】を提出する']"));
		dailyReport.click();

		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		WebElement textarea = webDriver.findElement(By.id("content_0"));
		textarea.sendKeys("テスト");
		getEvidence(new Object() {
		}, "01");

		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		WebElement dailyReport = webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));
		assertEquals("提出済み日報【デモ】を確認する", dailyReport.getAttribute("value"));

		getEvidence(new Object() {
		}, "02");
	}

}
