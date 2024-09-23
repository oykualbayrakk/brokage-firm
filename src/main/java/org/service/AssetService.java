package org.service;

import org.model.Asset;
import org.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> listAssets(String customerId) {
        return assetRepository.findAll()
            .stream()
            .filter(asset -> asset.getCustomerId().equals(customerId))
            .collect(Collectors.toList());
    }

    public void depositMoney(String customerId, double amount) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
            .orElseThrow(() -> new RuntimeException("Customer does not have TRY asset"));

        asset.setUsableSize(asset.getUsableSize() + (int) amount);
        assetRepository.save(asset);
    }

    public void withdrawMoney(String customerId, double amount, String iban) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
            .orElseThrow(() -> new RuntimeException("Customer does not have TRY asset"));

        if (asset.getUsableSize() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        asset.setUsableSize(asset.getUsableSize() - (int) amount);
        assetRepository.save(asset);
    }
}
