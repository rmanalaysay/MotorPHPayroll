/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Position class
public class Position {
    private int positionId;
    private String positionName;

    // Constructor
    public Position(int positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    // Getters
    public int getPositionId() { 
        return positionId; 
    }
    public String getPositionName() { 
        return positionName; 
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}

