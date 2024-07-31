import java.util.Scanner;

import Clases.*;

public class App {
    public static void main(String[] args) throws Exception {
        //Scanners
        Scanner scanInt = new Scanner(System.in);
        Scanner scanString = new Scanner(System.in);
        //Banderas de menus
        boolean banMenu = true, banAdmi = true, banAdmiPerf = true, banAdmiProy = true,
            banAdmiVerProy = true, banAdmiEqui = true, banAdmiTare = true, banAdmiDesa = true,
            banAdmiVerDesa = true, banAdmiDesaTare = true, banDesa = true, banDesaPerf = true,
            banDesaTare = true;
        //Variables
        int menu = 0;
        int leerInt = 0;    
        String leerString = "";
        //Instancias de clases
        Usuario usu = new Usuario();
        Usuario adm = new Usuario();
        Usuario dev = new Usuario();
        Proyecto pro = new Proyecto();
        Equipo equ = new Equipo();
        Tarea tar = new Tarea();
        Tablero tab = new Tablero();
        Avance ava = new Avance();
        
        
        //Modulo Inicio
        do {
            System.out.println("\tBienvenido a Q-Tasky\n");
            System.out.println("[1]\tRegistro");
            System.out.println("[2]\tIniciar sesión");
            menu = scanInt.nextInt();
            limpiarPantalla();

            switch (menu) {
                case 1:
                    //Registro
                    //Limpiar los datos del usuario
                    adm = new Usuario();
                    //Crear un usuario con puesto de Admin
                    adm.insertarUsuario(1,0);
                    break;
                case 2:
                    //Inicio de sesion
                    System.out.println("\tInicio de sesión\n");

                    //Limpiar los datos del usuario
                    usu = new Usuario();
                    //Retorna los permisos del usuario. Admin = Si, Dev = No.
                    leerString = usu.iniciarSesion();
                    if (leerString.equals("Si")) {
                        continuar();
                        adm = new Usuario();
                        adm = usu;
                        //Modulo Administrador
                        do {
                            System.out.println("\tBienvenido Administrador " + adm.getNombre_Usuario() +"\n");
                            System.out.println("[1]\tPerfil");
                            System.out.println("[2]\tProyectos");
                            System.out.println("[3]\tDesarrolladores");
                            System.out.println("[9]\tSalir");
                            menu = scanInt.nextInt();
                            limpiarPantalla();

                            switch (menu) {
                                case 1:
                                    //Perfil
                                    do {
                                        System.out.println("\tPerfil\n");
                                        System.out.println("[1]\tMostrar información");
                                        System.out.println("[2]\tEditar información");
                                        System.out.println("[9]\tSalir");
                                        menu = scanInt.nextInt();
                                        limpiarPantalla();

                                        switch (menu) {
                                            case 1:
                                                //Mostrar
                                                System.out.println("\tInformación de " + adm.getNombre_Usuario() + "\n");
                                                adm.mostrarInformacion();
                                                continuar();
                                                break;
                                            case 2:
                                                //Editar
                                                System.out.println("\tEditar información de " + adm.getNombre_Usuario() + "\n");
                                                adm.actualizarInformacion();
                                                continuar();
                                                break;
                                            case 9:
                                                //Salir
                                                banAdmiPerf = false;
                                                break;
                                        
                                            default:
                                                System.out.println("Opción incorrecta");
                                                continuar();
                                                break;
                                        }
                                    } while (banAdmiPerf);
                                    banAdmiPerf = true;
                                    
                                    break;
                                case 2:
                                    //Proyectos
                                    do {
                                        System.out.println("\tProyectos\n");
                                        System.out.println("[1]\tCrear Proyecto");
                                        System.out.println("[2]\tVer Proyectos");
                                        System.out.println("[9]\tSalir");
                                        menu = scanInt.nextInt();
                                        limpiarPantalla();

                                        switch (menu) {
                                            case 1:
                                                //Crear
                                                pro.insertarProyecto(adm.getId_Usuario());
                                                continuar();
                                                break;
                                            case 2:
                                                //Ver Proyectos
                                                //Muestra los proyectos del Admin y pide seleccionar uno
                                                System.out.println("\tProyectos\n");
                                                pro.verProyecto(adm.getId_Usuario());

                                                /*Antiguo metodo v3.9
                                                System.out.print("\nId Proyecto: ");
                                                leerInt = scanInt.nextInt();
                                                //Llena de info la instancia de proyecto con el id dado
                                                pro.consultarNombreProyecto(leerInt, adm.getId_Usuario());
                                                 */

                                                limpiarPantalla();

                                                //Proyecto
                                                do{
                                                    //Validar si el Proyecto es valido
                                                    if (pro.getNombre() == null) {
                                                        System.out.println("Sin proyectos para mostrar");
                                                        banAdmiVerProy = false;
                                                    }else if (pro.getNombre().equals("")) {
                                                        System.out.println("Proyecto inválido");
                                                        banAdmiVerProy = false;
                                                    } else{
                                                        System.out.println("\tProyecto " + pro.getNombre() + "\n");
                                                        System.out.println("[1]\tEditar proyecto");
                                                        System.out.println("[2]\tBorrar proyecto");
                                                        System.out.println("[3]\tCrear equipo");
                                                        System.out.println("[4]\tVer equipos");
                                                        System.out.println("[9]\tSalir");
                                                        menu = scanInt.nextInt();
                                                        limpiarPantalla();

                                                        switch (menu) {
                                                            case 1:
                                                                //Editar
                                                                pro.actualizarInformacion();
                                                                continuar();
                                                                break;
                                                            case 2:
                                                                //Borrar
                                                                pro.borrarProyecto();
                                                                banAdmiVerProy = false;
                                                                continuar();
                                                                break;
                                                            case 3:
                                                                //Crear Equipo a la par de su Tablero
                                                                equ.insertarEquipo(pro.getId_Proyecto());
                                                                pro.calcularEquipos();
                                                                tab.insertarTablero(equ.getId_Equipo());
                                                                continuar();
                                                                break;
                                                            case 4:
                                                                //Ver Equipos
                                                                System.out.println("\tEquipos\n");
                                                                equ.verEquipos(pro.getId_Proyecto());

                                                                /*Antiguo metodo v3.9
                                                                System.out.print("\nId Equipo: ");
                                                                leerInt = scanInt.nextInt();
                                                                equ.consultarNombreEquipo(leerInt, pro.getId_Proyecto());
                                                                */

                                                                limpiarPantalla();
                
                                                                //Equipo
                                                                do{
                                                                    //Validar si el Equipo es valido
                                                                    if (equ.getNombre_Equipo() == null) {
                                                                        System.out.println("No hay equipos para mostrar");
                                                                        banAdmiEqui = false;
                                                                    } else if (equ.getNombre_Equipo().equals("")) {
                                                                        System.out.println("Equipo inválido");
                                                                        banAdmiEqui = false;
                                                                    } else{
                                                                        System.out.println("\tEquipo " + equ.getNombre_Equipo() + "\n");
                                                                        System.out.println("[1]\tEditar equipo");
                                                                        System.out.println("[2]\tBorrar equipo");
                                                                        System.out.println("[3]\tCrear tarea");
                                                                        System.out.println("[4]\tVer tarea");
                                                                        System.out.println("[9]\tSalir");
                                                                        menu = scanInt.nextInt();
                                                                        limpiarPantalla();
                
                                                                        switch (menu) {
                                                                            case 1:
                                                                                //Editar
                                                                                equ.actualizarInformacion();
                                                                                continuar();
                                                                                break;
                                                                            case 2:
                                                                                //Borrar
                                                                                equ.borrarEquipo();
                                                                                pro.calcularEquipos();
                                                                                tab.calcularEstados(equ.getId_Equipo());
                                                                                tab.calcularAvance(equ.getId_Equipo());
                                                                                banAdmiEqui = false;
                                                                                continuar();
                                                                                break;
                                                                            case 3:
                                                                                //Crear Tarea a la par de su Avance
                                                                                tar.insertarTarea(pro.getId_Proyecto(), equ.getId_Equipo(), adm.getId_Usuario());
                                                                                ava.insertarAvance(tar.getId_Tarea(), equ.getId_Equipo());
                                                                                //Después de crear una tarea, se afecta el tablero y por tanto, el avance del proyecto
                                                                                tab.calcularEstados(equ.getId_Equipo());
                                                                                tab.calcularAvance(equ.getId_Equipo());
                                                                                continuar();
                                                                                break;
                                                                            case 4:
                                                                                //Ver
                                                                                System.out.println("\tTareas\n");
                                                                                tar.verTareas(equ.getId_Equipo());

                                                                                /*Antiguo metodo v3.9
                                                                                System.out.print("\nId Tarea: ");
                                                                                leerInt = scanInt.nextInt();
                                                                                tar.consultarNombreTarea(leerInt,equ.getId_Equipo());
                                                                                 */

                                                                                limpiarPantalla();
                                
                                                                                //Tarea
                                                                                do{
                                                                                    //Validar si la Tarea es valida
                                                                                    if (tar.getNombre() == null) {
                                                                                        System.out.println("No hay tareas para mostrar");
                                                                                        banAdmiTare = false;
                                                                                        continuar();
                                                                                    } else if (tar.getNombre().equals("")) {
                                                                                        System.out.println("Tarea inválida");
                                                                                        banAdmiTare = false;
                                                                                        continuar();
                                                                                    } else{
                                                                                        System.out.println("\tTarea " + tar.getNombre() + "\n");
                                                                                        System.out.println("[1]\tEditar tarea");
                                                                                        System.out.println("[2]\tBorrar tarea");
                                                                                        //Se podría añadir la opción "Asignar tarea" que despliegue todos
                                                                                        //los Devs y "Desasignar tarea" para liberarla 
                                                                                        System.out.println("[9]\tSalir");
                                                                                        menu = scanInt.nextInt();
                                                                                        limpiarPantalla();
                                
                                                                                        switch (menu) {
                                                                                            case 1:
                                                                                                //Editar
                                                                                                tar.actualizarInformacion();
                                                                                                continuar();
                                                                                                break;
                                                                                            case 2:
                                                                                                //Borrar
                                                                                                tar.borrarTarea();
                                                                                                //Recalcular el tablero porque hubo una modificacion en el avance de una tarea
                                                                                                tab.calcularEstados(tar.getEquipo());
                                                                                                tab.calcularAvance(tar.getEquipo());
                                                                                                banAdmiTare = false;
                                                                                                continuar();
                                                                                                break;
                                                                                            case 9:
                                                                                                banAdmiTare = false;
                                                                                                break;
                                                                                        
                                                                                            default:
                                                                                                System.out.println("Opción incorrecta");
                                                                                                continuar();
                                                                                                break;
                                                                                        }
                                                                                    }
                                                                                } while (banAdmiTare);
                                                                                banAdmiTare = true;
                                                                                break;
                                                                            case 9:
                                                                                banAdmiEqui = false;
                                                                                break;
                                                                        
                                                                            default:
                                                                                System.out.println("Opción incorrecta");
                                                                                continuar();
                                                                                break;
                                                                        }
                                                                    }
                                                                } while (banAdmiEqui);
                                                                banAdmiEqui = true;
                                                                
                                                                break;
                                                            case 9:
                                                                banAdmiVerProy = false;
                                                                break;
                                                        
                                                            default:
                                                                System.out.println("Opción incorrecta");
                                                                continuar();
                                                                break;
                                                        }
                                                    }
                                                } while (banAdmiVerProy);
                                                banAdmiVerProy = true;
                                                break;
                                            case 9:
                                                banAdmiProy = false;
                                                break;
                                        
                                            default:
                                                System.out.println("Opción incorrecta");
                                                continuar();
                                                break;
                                        }
                                    } while (banAdmiProy);
                                    banAdmiProy = true;

                                    break;
                                case 3:
                                    //Desarrolladores
                                    do {
                                        System.out.println("\tDesarrolladores\n");
                                        System.out.println("[1]\tCrear Desarrollador");
                                        System.out.println("[2]\tVer Desarrolladores");
                                        System.out.println("[9]\tSalir");
                                        menu = scanInt.nextInt();
                                        limpiarPantalla();

                                        switch (menu) {
                                            case 1:
                                                //Crear
                                                //Se usa la instancia dev para no afectar los datos de adm
                                                dev.insertarUsuario(2, adm.getId_Usuario());
                                                continuar();
                                                break;
                                            case 2:
                                                //Ver
                                                /*  Antiguo metodo v3.9
                                                adm.mostrarDesarrolladores();
                                                System.out.print("Id Desarrollador: ");
                                                //Aqui se guardara el ID del Dev para realizar modificaciones
                                                leerInt = scanInt.nextInt();
                                                dev.consultarNombreUsuario(leerInt, adm.getId_Usuario());
                                                */

                                                dev.mostrarDesarrolladores(adm.getId_Usuario());
                                                limpiarPantalla();
                                                //Desarrollador
                                                do{
                                                    //Validar si el dev es valido
                                                    if (dev.getNombre_Usuario() == null) {
                                                        System.out.println("No hay Desarrolladores para mostrar");
                                                        banAdmiVerDesa = false;
                                                        continuar();
                                                    }else if (dev.getNombre_Usuario().equals("")) {
                                                        System.out.println("Desarrollador invalido");
                                                        banAdmiVerDesa = false;
                                                        continuar();
                                                    } else{
                                                        System.out.println("\tDesarrollador " + dev.getNombre_Usuario() + "\n");
                                                        System.out.println("[1]\tEditar Desarrollador");
                                                        System.out.println("[2]\tBorrar Desarrollador");
                                                        System.out.println("[3]\tAsignar Tarea");
                                                        System.out.println("[4]\tVer Tareas");
                                                        System.out.println("[9]\tSalir");
                                                        menu = scanInt.nextInt();
                                                        limpiarPantalla();

                                                        switch (menu) {
                                                            case 1:
                                                                //Editar
                                                                dev.actualizarInformacion();
                                                                continuar();
                                                                break;
                                                            case 2:
                                                                //Borrar
                                                                dev.borrarUsuario();
                                                                //Recalcular el tablero porque hubo modificaciones en los avances
                                                                tab.calcularEstados(tar.getEquipo());
                                                                tab.calcularAvance(tar.getEquipo());
                                                                banAdmiVerDesa = false;
                                                                continuar();
                                                                break;
                                                            case 3:
                                                                //Asignar
                                                                dev.asignarTarea(adm.getId_Usuario());
                                                                continuar();
                                                                break;
                                                            case 4:
                                                                //Ver Tareas
                                                                System.out.println("\tTareas Asginadas\n");
                                                                tar.verTareasAsignadas(dev.getId_Usuario());
                                                                /*Antiguo metodo v3.9 
                                                                //Solicita el id de la tarea para ver mas opciones
                                                                System.out.print("\nId Tarea:");
                                                                leerInt = scanInt.nextInt();
                                                                tar.consultarNombreTareaAsignada(leerInt, dev.getId_Usuario());
                                                                */
                                                                limpiarPantalla();
                                                                //Tarea del Dev
                                                                do{
                                                                    //Validar si la Tarea es valida
                                                                    if (tar.getNombre() == null) {
                                                                        System.out.println("No hay tareas para mostrar");
                                                                        banAdmiDesaTare = false;
                                                                        continuar();
                                                                    } else if (tar.getNombre().equals("")) {
                                                                        System.out.println("Tarea inválida");
                                                                        banAdmiDesaTare = false;
                                                                        continuar();
                                                                    } else{
                                                                        System.out.println("\tTarea " + tar.getNombre() + "\n");
                                                                        System.out.println("[1]\tDesasignar tarea");
                                                                        System.out.println("[9]\tSalir");
                                                                        menu = scanInt.nextInt();
                                                                        limpiarPantalla();
                
                                                                        switch (menu) {
                                                                            case 1:
                                                                                //Desasignar
                                                                                tar.desasignarTarea();
                                                                                //Recalcular el tablero porque hubo una modificacion en el avance de una tarea
                                                                                tab.calcularEstados(tar.getEquipo());
                                                                                tab.calcularAvance(tar.getEquipo());
                                                                                banAdmiDesaTare = false;
                                                                                continuar();
                                                                                break;
                                                                            case 9:
                                                                                //Salir
                                                                                banAdmiDesaTare = false;
                                                                                break;
                                                                        
                                                                            default:
                                                                                System.out.println("Opción incorrecta");
                                                                                continuar();
                                                                                break;
                                                                        }
                                                                    }
                                                                } while (banAdmiDesaTare);
                                                                banAdmiDesaTare = true;
                                                                break;
                                                            case 9:
                                                                banAdmiVerDesa = false;
                                                                break;
                                                            default:
                                                                System.out.println("Opción incorrecta");
                                                                continuar();
                                                                break;
                                                        }
                                                    }
                                                    
                                                }while (banAdmiVerDesa);
                                                banAdmiVerDesa = true;
                                                break;
                                            case 9:
                                                banAdmiDesa = false;
                                                break;
                                            default:
                                                System.out.println("Opción incorrecta");
                                                continuar();
                                                break;
                                            
                                        }
                                    } while (banAdmiDesa);
                                    banAdmiDesa = true;
                                    break;
                                case 9:
                                    banAdmi = false;
                                    break;
                            
                                default:
                                    System.out.println("Opción incorrecta");
                                    continuar();
                                    break;
                            }
                        } while (banAdmi);
                        banAdmi = true;

                    }else if(leerString.equals("No")){
                        continuar();
                        dev = usu;
                        //Modulo Desarrollador
                        do {
                            System.out.println("\tBienvenido Desarrollador " + dev.getNombre_Usuario() + "\n");
                            System.out.println("[1]\tPerfil");
                            System.out.println("[2]\tVer tareas");
                            System.out.println("[9]\tSalir");
                            menu = scanInt.nextInt();
                            limpiarPantalla();

                            switch (menu) {
                                case 1:
                                    //Perfil
                                    do {
                                        System.out.println("\tPerfil\n");
                                        System.out.println("[1]\tMostrar información");
                                        System.out.println("[2]\tEditar información");
                                        System.out.println("[9]\tSalir");
                                        menu = scanInt.nextInt();
                                        limpiarPantalla();

                                        switch (menu) {
                                            case 1:
                                                //Mostrar Informacion
                                                System.out.println("\tInformación de " + dev.getNombre_Usuario() + "\n");
                                                dev.mostrarInformacion();
                                                continuar();
                                                break;
                                            case 2:
                                                //Editar Informacion
                                                System.out.println("\tEditar información de " + dev.getNombre_Usuario() + "\n");
                                                dev.actualizarInformacion();
                                                continuar();
                                                break;
                                            case 9:
                                                //Salir
                                                banDesaPerf = false;
                                                break;
                                        
                                            default:
                                                System.out.println("Opción incorrecta");
                                                continuar();
                                                break;
                                        }
                                    } while (banDesaPerf);
                                    banDesaPerf = true;
                                    break;
                                case 2:
                                    //Ver Tareas
                                    System.out.println("\tTareas Asginadas\n");
                                    tar.verTareasAsignadas(dev.getId_Usuario());
                                    /*Antiguo metodo v3.9
                                    //Solicita el id de la tarea para ver mas opciones
                                    System.out.print("\nId Tarea:");
                                    leerInt = scanInt.nextInt();
                                    tar.consultarNombreTareaAsignada(leerInt, dev.getId_Usuario());
                                     */
                                    limpiarPantalla();
                                    //Tarea del Dev
                                    do{
                                        //Validar si la Tarea es valida
                                        if (tar.getNombre() == null) {
                                            System.out.println("No hay Tareas asignadas");
                                            banDesaTare = false;
                                            continuar();
                                        } else if (tar.getNombre().equals("")) {
                                            System.out.println("Tarea inválida");
                                            banDesaTare = false;
                                            continuar();
                                        } else{
                                            System.out.println("\tTarea " + tar.getNombre() + "\n");
                                            System.out.println("[1]\tMarcar avance");
                                            System.out.println("[2]\tEscribir comentario");
                                            System.out.println("[9]\tSalir");
                                            menu = scanInt.nextInt();
                                            limpiarPantalla();

                                            switch (menu) {
                                                case 1:
                                                    //Marcar Avance
                                                    //Si el avance llega al 100%, entonces se sale de la tarea porque ya no estara asignada
                                                    if (tar.marcarAvance() == 100) {
                                                        banDesaTare = false;
                                                    }
                                                    //Recalcular el tablero porque se realiza un avance
                                                    tab.calcularEstados(tar.getEquipo());
                                                    tab.calcularAvance(tar.getEquipo());
                                                    continuar();
                                                    break;
                                                case 2:
                                                    //Comentario
                                                    tar.dejarComentario();
                                                    continuar();
                                                case 9:
                                                    //Salir
                                                    banDesaTare = false;
                                                    break;
                                            
                                                default:
                                                    System.out.println("Opción incorrecta");
                                                    continuar();
                                                    break;
                                            }
                                        }
                                    } while (banDesaTare);
                                    banDesaTare = true;
                                    
                                    break;
                                case 9:
                                    banDesa = false;
                                    break;

                                default:
                                    System.out.println("Opción incorrecta");
                                    continuar();
                                    break;
                            }
                        } while (banDesa);
                        banDesa = true;

                    }else{
                        System.out.println("\nUsuario o contraseña incorrecta");
                        continuar();
                    }
                    break;
            
                default:
                    System.out.println("Opción incorrecta");
                    continuar();
                    break;
            }
        } while (banMenu);
        banMenu = true;
    }

    public static void limpiarPantalla(){
        //Este comando causa problemas para imprimir registros
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static void continuar(){
        System.out.println("\nPresione ENTER para continuar...");
        System.console().readLine();
        limpiarPantalla();
    }
}