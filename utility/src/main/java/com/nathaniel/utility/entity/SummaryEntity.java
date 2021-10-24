package com.nathaniel.utility.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SummaryEntity {
    @Id(autoincrement = true)
    private long id;
    private int idx;
    private String iface;
    private String acct_tag_hex;
    private int uid_tag_int;
    private int cnt_set;
    private long rx_bytes;
    private int rx_packets;
    private long tx_bytes;
    private int tx_packets;
    private long rx_tcp_bytes;
    private int rx_tcp_packets;
    private long rx_udp_bytes;
    private int rx_udp_packets;
    private long rx_other_bytes;
    private int rx_other_packets;
    private long tx_tcp_bytes;
    private int tx_tcp_packets;
    private long tx_udp_bytes;
    private int tx_udp_packets;
    private long tx_other_bytes;
    private int tx_other_packets;

    @Generated(hash = 947167474)
    public SummaryEntity(long id, int idx, String iface, String acct_tag_hex,
                         int uid_tag_int, int cnt_set, long rx_bytes, int rx_packets,
                         long tx_bytes, int tx_packets, long rx_tcp_bytes, int rx_tcp_packets,
                         long rx_udp_bytes, int rx_udp_packets, long rx_other_bytes,
                         int rx_other_packets, long tx_tcp_bytes, int tx_tcp_packets,
                         long tx_udp_bytes, int tx_udp_packets, long tx_other_bytes,
                         int tx_other_packets) {
        this.id = id;
        this.idx = idx;
        this.iface = iface;
        this.acct_tag_hex = acct_tag_hex;
        this.uid_tag_int = uid_tag_int;
        this.cnt_set = cnt_set;
        this.rx_bytes = rx_bytes;
        this.rx_packets = rx_packets;
        this.tx_bytes = tx_bytes;
        this.tx_packets = tx_packets;
        this.rx_tcp_bytes = rx_tcp_bytes;
        this.rx_tcp_packets = rx_tcp_packets;
        this.rx_udp_bytes = rx_udp_bytes;
        this.rx_udp_packets = rx_udp_packets;
        this.rx_other_bytes = rx_other_bytes;
        this.rx_other_packets = rx_other_packets;
        this.tx_tcp_bytes = tx_tcp_bytes;
        this.tx_tcp_packets = tx_tcp_packets;
        this.tx_udp_bytes = tx_udp_bytes;
        this.tx_udp_packets = tx_udp_packets;
        this.tx_other_bytes = tx_other_bytes;
        this.tx_other_packets = tx_other_packets;
    }

    @Generated(hash = 641901020)
    public SummaryEntity() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getIface() {
        return this.iface;
    }

    public void setIface(String iface) {
        this.iface = iface;
    }

    public String getAcct_tag_hex() {
        return this.acct_tag_hex;
    }

    public void setAcct_tag_hex(String acct_tag_hex) {
        this.acct_tag_hex = acct_tag_hex;
    }

    public int getUid_tag_int() {
        return this.uid_tag_int;
    }

    public void setUid_tag_int(int uid_tag_int) {
        this.uid_tag_int = uid_tag_int;
    }

    public int getCnt_set() {
        return this.cnt_set;
    }

    public void setCnt_set(int cnt_set) {
        this.cnt_set = cnt_set;
    }

    public long getRx_bytes() {
        return this.rx_bytes;
    }

    public void setRx_bytes(long rx_bytes) {
        this.rx_bytes = rx_bytes;
    }

    public int getRx_packets() {
        return this.rx_packets;
    }

    public void setRx_packets(int rx_packets) {
        this.rx_packets = rx_packets;
    }

    public long getTx_bytes() {
        return this.tx_bytes;
    }

    public void setTx_bytes(long tx_bytes) {
        this.tx_bytes = tx_bytes;
    }

    public int getTx_packets() {
        return this.tx_packets;
    }

    public void setTx_packets(int tx_packets) {
        this.tx_packets = tx_packets;
    }

    public long getRx_tcp_bytes() {
        return this.rx_tcp_bytes;
    }

    public void setRx_tcp_bytes(long rx_tcp_bytes) {
        this.rx_tcp_bytes = rx_tcp_bytes;
    }

    public int getRx_tcp_packets() {
        return this.rx_tcp_packets;
    }

    public void setRx_tcp_packets(int rx_tcp_packets) {
        this.rx_tcp_packets = rx_tcp_packets;
    }

    public long getRx_udp_bytes() {
        return this.rx_udp_bytes;
    }

    public void setRx_udp_bytes(long rx_udp_bytes) {
        this.rx_udp_bytes = rx_udp_bytes;
    }

    public int getRx_udp_packets() {
        return this.rx_udp_packets;
    }

    public void setRx_udp_packets(int rx_udp_packets) {
        this.rx_udp_packets = rx_udp_packets;
    }

    public long getRx_other_bytes() {
        return this.rx_other_bytes;
    }

    public void setRx_other_bytes(long rx_other_bytes) {
        this.rx_other_bytes = rx_other_bytes;
    }

    public int getRx_other_packets() {
        return this.rx_other_packets;
    }

    public void setRx_other_packets(int rx_other_packets) {
        this.rx_other_packets = rx_other_packets;
    }

    public long getTx_tcp_bytes() {
        return this.tx_tcp_bytes;
    }

    public void setTx_tcp_bytes(long tx_tcp_bytes) {
        this.tx_tcp_bytes = tx_tcp_bytes;
    }

    public int getTx_tcp_packets() {
        return this.tx_tcp_packets;
    }

    public void setTx_tcp_packets(int tx_tcp_packets) {
        this.tx_tcp_packets = tx_tcp_packets;
    }

    public long getTx_udp_bytes() {
        return this.tx_udp_bytes;
    }

    public void setTx_udp_bytes(long tx_udp_bytes) {
        this.tx_udp_bytes = tx_udp_bytes;
    }

    public int getTx_udp_packets() {
        return this.tx_udp_packets;
    }

    public void setTx_udp_packets(int tx_udp_packets) {
        this.tx_udp_packets = tx_udp_packets;
    }

    public long getTx_other_bytes() {
        return this.tx_other_bytes;
    }

    public void setTx_other_bytes(long tx_other_bytes) {
        this.tx_other_bytes = tx_other_bytes;
    }

    public int getTx_other_packets() {
        return this.tx_other_packets;
    }

    public void setTx_other_packets(int tx_other_packets) {
        this.tx_other_packets = tx_other_packets;
    }
}
