package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * 
 * @author katsu
 * administratorクラスを操作するリポジトリ(Dao)
 * ・管理者情報を挿入する処理
 * ・メールアドレスとパスワードから管理者情報を取得する処理
 */
@Repository
public class AdministratorRepository {
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	// administratorsテーブルを操作するメソッドの定義
	public void insert(Administrator administrator) { // 管理者情報を挿入する処理（入力された氏名、メールアドレス、パスワードをVALUESに入れてテーブルに登録したい）
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		String sql = "INSERT INTO administrators(id, name, mailAddress, password) "
				+ "VALUES(:id, :name, :mailAddress, :password);";
		template.update(sql, param);
		
	}

	// メールアドレスとパスワードから管理者情報を取得するメソッド
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id,name,mailAddress,password FROM administrators WHERE mailAddress=:mailAddress, password=:password;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",password);
		// addValue()は1つずつ行う。
		
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if (administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0); // 型の不一致が起きている。戻り値はAdministratorで指定されているので、メソッドのデータ型を修正。
	}

}
