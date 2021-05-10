package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 
 * @author katsu
 * employeesテーブルを操作するリポジトリクラス
 *
 */

@Repository
public class EmployeeRepository {
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hireDate")); // ？hireDateではなくgetDate。ResultSetにget"Hire"Dateという個別具体的なメソッドはないためエラー。要は日付をとりたいためgetDateに修正
		employee.setMailAddress(rs.getString("mailAddress"));
		employee.setZipCode(rs.getString("zipCode"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("saraly"));
		employee.setCharacteristics(rs.getString("characteristics"));
		return employee;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	// loadメソッド 主キーから従業員情報を取得する
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		Employee employee  = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		
		return employee;
	}
	
	// findAllメソッド　従業員⼀覧情報を入社日順(降順)で取得する
	public List<Employee> findAll(){
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC;";
		
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	
	// updateメソッド　従業員情報を変更する、全行更新されないように Where 句の指定を考える。
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		
		String sql = "UPDATE employees SET name=:name, image=:image, gender=:gender, hireDate=:hireDate, maillAddress=:mailAddress,"
				+ " zipCode=:zipCode, address=:address, telephone=:telephone, saraly=:saraly, characteristics=:characteristics "
				+ "WHERE id=:id;";
		template.update(sql, param);
	}

}
