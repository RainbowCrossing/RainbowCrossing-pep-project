package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;
    // Constructors.
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // Retrieve all social media accounts.
    public List<Account> getAllAccountsService(){
        return accountDAO.getAllAccounts();
    }

    // Retrieve account by an id.
    public Account getAccountByIdService(int id){
        return null;
    }

    // Inserting a new account. Using if statements to verify username/password
    // is accepted. Along with checking if account username already exists.
    public Account createNewAccountSerivce(Account account){
        if(account.getUsername().isBlank() || account.getPassword().isBlank()){
            System.out.println("Username/Password is invalid.");
            return null;
        }
        if(account.getPassword().length() < 4){
            System.out.println("Password must be at least 4 characters long.");
            return null;
        }
        List<Account> results = this.getAllAccountsService();
        if(results.contains(account)){
            System.out.println("Account already exists.");
            return null;
        }
        Account createAccount = new Account();
        createAccount = accountDAO.insertNewAccount(account);

        return createAccount;
    }

    // Finding a specific account.
    public Account findAccountService(Account account){
        Account accountSearch = new Account();
        accountSearch = accountDAO.findAccount(account);
        return accountSearch;
    }

}
