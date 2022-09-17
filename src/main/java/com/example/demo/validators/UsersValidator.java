package com.example.demo.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Users;
import com.example.demo.repositories.UsersRepository;

@Component
public class UsersValidator {

    @Autowired
    UsersRepository usersRepository; // リポジトリ

    public List<String> validate(Users u, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {

        List<String> errors = new ArrayList<String>();

        //名前の確認
        String name_error = validateName(u.getName(), codeDuplicateCheckFlag);
        if (!name_error.equals("")) {
            errors.add(name_error);
        }

        //パスワードのチェック
        String passError = validatePassword(u.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private String validateName(String name, Boolean codeDuplicateCheckFlag) {

        if (name == null || name.equals("")) {
            return "ニックネームが入力されていません";
        }

        // すでに登録されている名前があるか確認

        if(codeDuplicateCheckFlag) {
            long users_count = usersRepository.countByNameEquals(name);
            if(users_count > 0) {
                return "入力された名前はすでに使用されています。";
            }
        }
        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param password パスワード
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return "パスワードが入力されていません";
        }

        //エラーがない場合は空文字を返却
        return "";
    }

}
