package com.ljl.hadoopconf;


import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author ：
 * @date ：Created in 2019/11/5 11:35
 * @description：
 * @modified By：
 * @version:
 */
@Getter
@Setter
public class User implements Writable {

    private String username;
    private Integer age;
    private String address;


    @Override
    public void write(DataOutput dataOutput) throws IOException {
    // 把对象序列化
        dataOutput.writeChars(username);
        dataOutput.writeInt(age);
        dataOutput.writeChars(address);


    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        // 把序列化的对象读取到内存中
        username = dataInput.readUTF();
        age = dataInput.readInt();
        address = dataInput.readUTF();

    }
}
