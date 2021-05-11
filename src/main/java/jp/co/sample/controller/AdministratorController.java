package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 
 * @author katsu
 * 管理者登録画面を表示する処理
 * loginForm、administrator/login.htmlへのフォワード処理追記
 */

@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AdministratorService administratorService;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	// 管理者情報を登録する
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator(); // ドメインインスタンス化
		BeanUtils.copyProperties(form, administrator); // InsertAdministratorFormオブジェクトの中身を、インスタンス化したadministrator ドメインオブジェクトにコピーする
		administratorService.insert(administrator); // administratorServiceのinsertメソッドを使う記述が抜けている？
		return "/"; // ログイン画面にリダイレクト
	}
	
	// LoginFormをインスタンス化 → そのままreturn
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
	
	// administrator/login.htmlにフォワードする処理
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	// メソッド定義
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword()) ; // LoginFromオブジェクトの中にあるメールアドレスとパスワードを引数に渡す。Administratorオブジェクトが戻ってくるので受け取る（変数に入れる？）
		
		if(administrator == null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが不正です"); // エラーメッセージをModelオブジェクト（リクエストスコープ）にセットする。
			return "administrator/login"; 
		} else {
			session.setAttribute("administratorName", administrator.getName()); // sessionスコープにadministratorNameという名前をつけて管理者名を格納
			return "forward:/employee/showList"; // 次に作成する従業員情報一覧ページにフォワードする
		}
	}

}
