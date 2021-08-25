package com.wordgame.recordsstorage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "standard_records")
public class StandardGameRecord extends GameRecord {
}
