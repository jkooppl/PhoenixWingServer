package com.pizza73.service;

import java.io.File;

import com.pizza73.model.CompleteCart;

public interface AsyncProcessorService
{

    public void updateEmployees(File file);

    public void copyOrderToDisk(String fileName, String textOrder);

    public void sendEmailConfirmation(CompleteCart completeCart, String email);
}
