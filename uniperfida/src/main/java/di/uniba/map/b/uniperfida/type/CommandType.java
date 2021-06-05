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
    END, INVENTORY, NORTH, SOUTH, EAST, WEST, OPEN, PUSH, PICK_UP, LEAVE, TALK, USE, LOOK, GO_UP, GO_DOWN, HELP, READ
}
