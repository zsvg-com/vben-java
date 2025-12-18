package vben.common.jdbc.dto;

import lombok.Data;

@Data
public class SidName
{
    private String id;
    private String name;

    @Override
    public String toString() {
        return "SidName{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
