package vben.common.jdbc.dto;

import lombok.Data;

@Data
public class LidName
{
    private long id;
    private String name;

    @Override
    public String toString() {
        return "SidName{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
