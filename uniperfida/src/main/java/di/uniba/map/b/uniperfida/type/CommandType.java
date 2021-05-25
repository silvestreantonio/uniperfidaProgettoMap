/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.type;

/**
 *
 * @author pierpaolo
 */

// comandi che il parser puo capire in maniera generica (tipo enumerativo)
public enum CommandType {
    END, INVENTORY, NORD, SOUTH, EAST, WEST, OPEN, CLOSE, PUSH, PULL, WALK_TO, PICK_UP, TALK_TO, GIVE, USE, LOOK_AT, TURN_ON, TURN_OFF, GO_UP, GO_DOWN
}
