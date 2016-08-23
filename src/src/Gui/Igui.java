/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Datos.Assets.Assets;

/**
 *
 * @author César
 */
public interface Igui {

    public Assets[] getAssets();

    public void nuevoAssets(Assets a);

    public void mostrar(Object message, String title, int messageType);

}
