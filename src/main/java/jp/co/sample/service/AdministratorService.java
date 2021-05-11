package jp.co.sample.service;
/**
 * 管理者情報を挿入するメソッド追加
 * 
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	// 管理者情報を挿入する。Repositoryのinsert()メソッドを呼ぶ
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}
	
	// ログイン処理
	public Administrator login(String mailAddress, String password) {
		Administrator admin = administratorRepository.findByMailAddressAndPassword(mailAddress, password);
		return admin; // 戻ってきた管理者情報をそのまま呼び出し、元に返す？戻り値がある=原則、代入したい
	}
	

}
