package com.example.walet.controller;

import com.example.walet.model.OperationType;
import com.example.walet.model.WalletDto;
import com.example.walet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private WalletService walletService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void changeBalanceOfWallet() throws Exception {
        WalletDto walletDto = new WalletDto(UUID.randomUUID(), OperationType.DEPOSIT, 1000);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/wallet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletDto))
        )
                .andExpect(status().isCreated());
//        Mockito.verify(walletService, Mockito.times(1)).changeAmount(walletDto);
    }

    @Test
    public void getCurrentBalanceOfWalletTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(walletService.findOutCurrentBalance(uuid)).thenReturn(100);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/wallets/{WALLET_UUID}", uuid.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100));

        Mockito.verify(walletService, Mockito.times(1)).findOutCurrentBalance(uuid);
    }
}