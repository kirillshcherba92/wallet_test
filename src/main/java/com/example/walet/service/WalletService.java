package com.example.walet.service;

import com.example.walet.dao.WalletDao;
import com.example.walet.dao.WalletOperationDao;
import com.example.walet.exception.WalletException;
import com.example.walet.model.OperationType;
import com.example.walet.model.WalletDto;
import com.example.walet.model.WalletModel;
import com.example.walet.model.WalletOperationModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WalletService {

    private Map<OperationType, CalculateAmount> calculateAmountMap;

    private final WalletDao walletDao;
    private final WalletOperationDao walletOperationDao;

    public WalletService(WalletDao walletDao, WalletOperationDao walletOperationDao) {
        this.walletDao = walletDao;
        this.walletOperationDao = walletOperationDao;
        calculateAmountMap = initOperationTypeCalculate();
    }

    private Map<OperationType, CalculateAmount> initOperationTypeCalculate() {
        calculateAmountMap = new EnumMap<>(OperationType.class);
        calculateAmountMap.put(OperationType.DEPOSIT, Integer::sum);
        calculateAmountMap.put(OperationType.WITHDRAW, (startAmount, inputAmount) -> {
            if (startAmount < inputAmount) {
                throw new WalletException("На счёте недостаточно средств");
            }
            return startAmount - inputAmount;
        });
        return calculateAmountMap;
    }

    @Transactional
    public void changeAmount(WalletDto walletDto) {
        // TODO Добавить кештрование
        if (walletDto.getAmount() <= 0) {
            throw new WalletException("Cумма для операции должна быть больше 0");
        }
        WalletModel walletModelById = walletDao.findById(walletDto.getWalletId())
                .orElseThrow(() -> new WalletException(
                                String.format("Кошелька с UUID : \"%s\" не существует!\n" +
                                                " Опарция %s не допустима.",
                                        walletDto.getWalletId(), walletDto.getOperationType().getName())
                        )
                );
        int calculateAmount = calculateAmountMap.get(walletDto.getOperationType()).calculate(walletModelById.getAmount(), walletDto.getAmount());
        walletDao.update(walletDto.getWalletId(), calculateAmount);

        walletOperationDao.create(new WalletOperationModel(null, walletDto.getOperationType(), walletDto.getAmount(), walletDto.getWalletId().toString()));
    }

    public int findOutCurrentBalance(UUID id) {
        WalletModel walletModelById = walletDao.findById(id)
                .orElseThrow(() -> new WalletException(
                                String.format("Кошелька с UUID : \"%s\" не существует!\n" +
                                        " Опарция проверки баланса не допустима.", id)
                        )
                );
        return walletModelById.getAmount();
    }
}
