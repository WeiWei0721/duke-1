package com.weiwei.storage;


import com.weiwei.task.Deadline;
import com.weiwei.task.Event;
import com.weiwei.task.Task;
import com.weiwei.task.Todo;
import com.weiwei.util.DateUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encodes the {@code TaskList} object into a data file for storage.
 */
public class TaskListEncoder {
    /**
     * Encodes a single {@code Task} in the {@code toSave} into a list of decodable and readable string presentation
     * for storage.
     */
    public static void encodeTask(Task toSave, String pathOfFileToSave) throws IOException {
        String encodedTask = encodeTaskToString(toSave);
        FileWriter fw = new FileWriter(pathOfFileToSave, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(encodedTask);
        bw.newLine();
        bw.close();
    }


    /**
     * Encodes all the {@code Task} in the {@code toSave} into a list of decodable and readable string presentation
     * for storage.
     */

    public static void encodeTaskList(List<Task> toSave, String pathOfFileToSave) throws IOException {
        final List<String> encodeTaskList = new ArrayList<>();
        FileWriter fw = new FileWriter(pathOfFileToSave, false);//not append , overwrite.
        BufferedWriter bw = new BufferedWriter(fw);
        for (Task individualTask : toSave) {
            encodeTaskList.add(encodeTaskToString(individualTask));
        }
        for (String singleTask : encodeTaskList) {
            bw.write(singleTask);
            bw.newLine();
        }
        bw.close();

    }


    /**
     * Encodes the {@code Task} into a decodable and readable string representation.
     */
    private static String encodeTaskToString(Task task) {

        StringBuilder encodeTaskListBuilder = new StringBuilder();

        if (task instanceof Todo) {
            encodeTaskListBuilder.append(TaskListEnum.T);
            encodeTaskListBuilder = appendEncodedTask(encodeTaskListBuilder, task);
        } else if (task instanceof Deadline d) {
            encodeTaskListBuilder.append(TaskListEnum.D);
            encodeTaskListBuilder = appendEncodedTask(encodeTaskListBuilder, task);
            encodeTaskListBuilder.append(" | ");
            encodeTaskListBuilder.append(DateUtil.parseDateForStorage(d.getBy()));
        } else if (task instanceof Event e) {
            encodeTaskListBuilder.append(TaskListEnum.E);
            encodeTaskListBuilder = appendEncodedTask(encodeTaskListBuilder, task);
            encodeTaskListBuilder.append(" | ");
            encodeTaskListBuilder.append(DateUtil.parseDateForStorage(e.getStart()));
            encodeTaskListBuilder.append(" - ");
            encodeTaskListBuilder.append(DateUtil.parseDateForStorage(e.getEnd()));
        }


        return encodeTaskListBuilder.toString();
    }

    /**
     * Helper method that construct the String for the encoded Task in the text field
     *
     * @param sb   StringBuilder
     * @param task Task
     * @return Partial Construction of the task in the specified txt file
     */
    private static StringBuilder appendEncodedTask(StringBuilder sb, Task task) {
        sb.append(" | ");
        sb.append(task.getStatusIcon().equals("X") ? "1" : 0);
        sb.append(" | ");
        sb.append(task.getDescription());
        return sb;

    }


}
