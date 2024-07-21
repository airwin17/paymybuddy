package com.pmb.paymybuddy.dto;

public class TransactionDTO {
    private String relationship;
    private String relationshipEmail;
    private Double amount;
    private String description;

    public TransactionDTO() {

    }

    public TransactionDTO(String relationship, Double amount, String description) {
        this.relationship = relationship;
        this.amount = amount;
        this.description = description;
    }
    public String getRelationshipEmail() {
        return relationshipEmail;
    }

    public void setRelationshipEmail(String relationshipEmail) {
        this.relationshipEmail = relationshipEmail;
    }
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
