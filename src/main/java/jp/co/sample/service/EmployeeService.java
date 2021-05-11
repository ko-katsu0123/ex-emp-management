package jp.co.sample.service;
/**
 * 従業員情報を全件検索する
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// 従業員情報の全件取得
	public List<Employee> showList(){
		List<Employee> employeeShowList = employeeRepository.findAll(); // 降順の全件情報のリストが右辺になる
		return employeeShowList;
	}

}
