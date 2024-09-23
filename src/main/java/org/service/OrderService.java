package org.service;

import org.model.Asset;
import org.model.Order;
import org.repository.AssetRepository;
import org.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AssetRepository assetRepository;

    public Order createOrder(Order order) {
        // Check if enough usable size is available
        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
            .orElseThrow(() -> new RuntimeException("Customer does not have TRY asset"));

        if (asset.getUsableSize() < order.getSize() * order.getPrice()) {
            throw new RuntimeException("Insufficient funds");
        }

        // Deduct funds
        asset.setUsableSize((int) (asset.getUsableSize() - (order.getSize() * order.getPrice())));
        assetRepository.save(asset);

        order.setStatus("PENDING");
        order.setCreateDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> listOrders(String customerId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public void deleteOrder(Long id, String customerId) {
        Order order = orderRepository.findByIdAndCustomerId(id, customerId)
            .orElseThrow(() -> new RuntimeException("Order not found or does not belong to customer"));
        
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be deleted");
        }

        // Refund funds
        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
            .orElseThrow(() -> new RuntimeException("Customer does not have TRY asset"));
        asset.setUsableSize((int) (asset.getUsableSize() + (order.getSize() * order.getPrice())));
        assetRepository.save(asset);

        orderRepository.delete(order);
    }

    // More methods for matching orders, etc.
}
