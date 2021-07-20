package com.grace.budgtey;

import com.grace.budgtey.database.entity.TransactionEntity;

public interface ListItemClickListener {
    void onListItemClick(TransactionEntity transactionEntity);

    void onListItemLongClick(TransactionEntity transactionEntity);
}
