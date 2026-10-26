package net.rafiky.ebankservice.service;

import net.rafiky.customerservice.repository.CustomerRepository;
import net.rafiky.ebankservice.entities.BankAccount;
import net.rafiky.ebankservice.feign.CustomerRestClient;
import net.rafiky.ebankservice.model.Customer;
import net.rafiky.ebankservice.repository.BankAccountrepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountrepository accountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountrepository accountRepository , CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    public List<BankAccount> getAllBankAccounts() {
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id) {
        BankAccount bankAccount= accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Bank account not found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }

    public BankAccount save(BankAccount bankAccount) {
        try{
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            return accountRepository.save(bankAccount);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
