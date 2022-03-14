package project.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "device", schema = "public", catalog = "mercury2022")
public class DevicesEntity {
    @Id
    private long id;
    private String ip;
    private int port;
    private int serialNumber;
    private Integer networkAdress;
//    private Res res_id;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ip", nullable = false, length = 15)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "port", nullable = false)
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Basic
    @Column(name = "serial_number", nullable = false)
    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "network_adress", nullable = true)
    public Integer getNetworkAdress() {
        return networkAdress;
    }

    public void setNetworkAdress(Integer networkAdress) {
        this.networkAdress = networkAdress;
    }

//    @JoinColumn(name = "res_id", referencedColumnName = "id")
//    public Res getRes_id() {
//        return res_id;
//    }
//
//    public void setRes(Res res) {
//        this.res_id = res;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevicesEntity that = (DevicesEntity) o;
        return id == that.id &&
                port == that.port &&
                serialNumber == that.serialNumber &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(networkAdress, that.networkAdress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ip, port, serialNumber, networkAdress);
    }
}
