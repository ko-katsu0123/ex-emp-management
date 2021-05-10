package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

/**
 * 
 * @author katsu
 * 管理者登録画面を表示する処理
 */

@Controller
@RequestMapping("/")
public class AdministratorController {
	
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

}
