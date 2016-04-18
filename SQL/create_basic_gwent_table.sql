/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Mengchuan Lin
 * Created: Apr 16, 2016
 */

Prompt ****** Creating BASIC_GWENT table ....

CREATE TABLE basic_gwent
  ( cardName     VARCHAR2(80)
    CONSTRAINT card_name_nn NOT NULL
  , unitStrength CHAR(2)
    CONSTRAINT chk_unit_str_limit
          CHECK(LENGTH(unitStrength) <= 2)
  , ability      VARCHAR2(100)
  , cardType     VARCHAR2(6)
  , CONSTRAINT card_name_pk
              PRIMARY KEY(cardName)
  );

COMMIT;
