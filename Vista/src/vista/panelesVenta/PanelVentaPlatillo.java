/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.panelesVenta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javafx.scene.Parent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logica.Control;
import negocio.Cliente;
import negocio.Platillo;
import negocio.PlatilloMenu;
import negocio.ReservaPlatillo;
import negocio.Venta;
import negocio.VentaPlatillo;
import vista.ConfirmacionVenta;
import vista.SeleccionCliente;
import vista.Seleccionreserva;

/**
 *
 * @author ed000
 */
public class PanelVentaPlatillo extends javax.swing.JPanel {
int cMax =1;
    private int diaSemana;
    private String categoría;
    private Platillo sopaDelDia;
    private float total;
    //////////////        MENU
    // Lista original del menu, solo se modifica cuando se genera la venta
    private ArrayList<PlatilloMenu> listaMenu;
    // Lista copia del mmenu, se va modificando al agregar platillos a la venta
    private ArrayList<PlatilloMenu> listamenuCopia;
    // La lista original toma el valor de la copia cuando se genera la venta
    /////////////////////////////////////////////////////////////////////

    // Lista de los platillos a  vender se utiliza por la tabla "Platillos a vender"
    private ArrayList<VentaPlatillo> platillosAVender;
    /////////////////////////////////////////////////////////////////////

    ////////////////// RESERVAS
    // Lista de reservas que se actualizará al agregar un platillo a la lista siendo reserva
    private ArrayList<ReservaPlatillo> reservasSeleccionadas;
    //////////////////////////////////////////////////////////////

    ////////////////// CLIENTES
    // Lista de clientes que se actualizará cuando se seleccione algún cliente con crédito
    private ArrayList<Cliente> clientesSeleccionados;
    private ArrayList<Integer> creditosUsados;
    private SeleccionCliente modalSeleccionCliente;
    //////////////////////////////////////////////////////////////

    //Modal que se abrirá para seleccionar la reserva
    private Seleccionreserva modalSeleccionreserva;

    ///           PLATILLOS PARA LLEVAR
    // Se usa cuando se imprime la comanda
    // Lista con los indices de los platillos que son para llevar, se resetea al borrar los datos o generar la venta
    private ArrayList<Integer> listaParaLLevar;
    /**
     * Creates new form PanelVentaPlatillo
     */
    private Frame parent;

    public PanelVentaPlatillo(Frame parent, int diaSemana, String categoria, ArrayList<PlatilloMenu> listaMenu) {
        initComponents();
        this.parent = parent;
        // Establece los parametros recibidos
        this.diaSemana = diaSemana;
        this.categoría = categoria;
        this.listaMenu = listaMenu;
        this.reservasSeleccionadas = new ArrayList<>();
        this.clientesSeleccionados = new ArrayList<>();
        this.listamenuCopia = new ArrayList<>(listaMenu);
        this.listaParaLLevar = new ArrayList<>();
        this.creditosUsados = new ArrayList<>();
        platillosAVender = new ArrayList<>();
        txtCosto.setValue(0f);
        total = 0f;

        // Establece el menú de platillos a la venta con la lista original
        establecerMenu(listaMenu);
    }

    private void establecerMenu(ArrayList<PlatilloMenu> lMenu) {
        /// Crea los modelos para establecer en la tabla de menu y en el combobox
        Object[] columnas = {"Platillo", "Reservados", "Para vender"};
        Object[][] modelo = new Object[lMenu.size()][3];
        int x = 0;
        DefaultComboBoxModel modeloLista;
        ArrayList<Platillo> listaPlatilllos = new ArrayList<>();
        for (PlatilloMenu platilloMenu : lMenu) {
            int paraVender = platilloMenu.getCantidad();
            int reservados = platilloMenu.getReservados();
            // Si el platillo del menu aún se puede vender
            if (paraVender > 0 || reservados > 0) {
                // Se agrega el platillo a la lista de platillos
                listaPlatilllos.add(platilloMenu.getPlatillo());
            }
            modelo[x][0] = platilloMenu.getPlatillo().getNombre();
            modelo[x][1] = reservados;
            modelo[x][2] = paraVender;
            x++;
        }
        // Se establece el modelo en la tabla con los datos
        tablaMenu.setDefaultEditor(Object.class, null);
        tablaMenu.setModel(new DefaultTableModel(modelo, columnas));
        tablaMenu.setCellSelectionEnabled(false);
        tablaMenu.setRowSelectionAllowed(false);
        Object[] arregloPlatillos;
        // Si hay platillos que se puedan vender
        if (listaPlatilllos.size() > 0) {
            // Se guardan en el arreglo
            arregloPlatillos = listaPlatilllos.toArray();
            modeloLista = new DefaultComboBoxModel(arregloPlatillos);
            comboPlatillos.setModel(modeloLista);
            // Si es horario de comidas
            if (this.categoría.equals("COMIDA")) {
                // Consulta la sopa del día
                this.sopaDelDia = Control.menu.consultarSopaDia(diaSemana);
                // si hay una sopa establecida en el menú
                if (sopaDelDia != null) {
                    // Se habilita el checkbox y se establece el nombre
                    checkBoxSopa.setEnabled(true);
                    checkBoxSopa.setText(sopaDelDia.getNombre());
                } else {
                    checkBoxSopa.setEnabled(false);
                    checkBoxSopa.setText("SOPA NO ESTABLECIDA");
                }
            } else {
                checkBoxSopa.setEnabled(false);
            }
        }//Si no
        else {
            // Se guarda un solo valor indicando el resultado de la búsqueda
            arregloPlatillos = new Object[1];
            arregloPlatillos[0] = "-------SIN PLATILLOS DISPONIBLES------";
            modeloLista = new DefaultComboBoxModel(arregloPlatillos);
            comboPlatillos.setModel(modeloLista);
            // Se desactivan todos los componentes
            desactivarComponentes(this);
        }
    }

    private void lanzarModalreservas(ArrayList<ReservaPlatillo> lReservas) {
        // Se abre el dialog para seleccionar la reserva
        modalSeleccionreserva = new Seleccionreserva(parent, true, lReservas);
        modalSeleccionreserva.setVisible(true);
        // Si se seleccionó una reserva en el dialog
        if (modalSeleccionreserva.reservaSeleccionada != null) {
            // Se establecen los datos en los campos
            estableceDatosReserva();
            btnBorrarListaPlatillos.setEnabled(false);

        }// Si no se seleccionó nada
        else {
            // Vuelve igual
            reestableceCamposPorReserva();
            if (platillosAVender.size() > 0) {
                btnBorrarListaPlatillos.setEnabled(true);
            }

        }
    }

    private void lanzarModalClientes(ArrayList<Cliente> lClientes) {
        // Se abre el dialog para seleccionar el cliente
        modalSeleccionCliente = new SeleccionCliente(parent, true, categoría, lClientes);
        modalSeleccionCliente.setVisible(true);
        // Si se seleccionó un cliente en el dialog
        if (modalSeleccionCliente.clienteSeleccionado != null) {
            // Se establece los datos de este
            spinnercantidad.setEnabled(false);
            checkBoxEsReserva.setEnabled(false);
            txtCosto.setValue(0f);
            btnBorrarListaPlatillos.setEnabled(false);
        }// Si no se seleccionó nada
        else {
            checkBoxCredito.setSelected(false);
            spinnercantidad.setEnabled(true);
            if (platillosAVender.size() > 0) {
                btnBorrarListaPlatillos.setEnabled(true);
            }
        }
    }

    private void estableceDatosReserva() {
        comboPlatillos.setEnabled(false);
        spinnercantidad.setValue(modalSeleccionreserva.reservaSeleccionada.getCantidad());
        spinnercantidad.setEnabled(false);
        txtCosto.setValue(0f);
        checkBoxCredito.setEnabled(false);

    }

    private void reestableceCamposPorReserva() {
        reservasSeleccionadas.remove(modalSeleccionreserva.reservaSeleccionada);
        comboPlatillos.setEnabled(true);
        spinnercantidad.setValue(1);
        spinnercantidad.setEnabled(true);
        checkBoxEsReserva.setSelected(false);
        checkBoxCredito.setEnabled(true);

    }

    private void reestableceCamposPorAgregado() {
        comboPlatillos.setEnabled(true);
        spinnercantidad.setValue(1);
        spinnercantidad.setEnabled(true);
        checkBoxEsReserva.setEnabled(true);
        checkBoxEsReserva.setSelected(false);
        checkBoxSopa.setSelected(false);
        checkBoxParaLlevar.setSelected(false);
        checkBoxCredito.setEnabled(true);
        checkBoxCredito.setSelected(false);
        txtCosto.setValue(0f);
    }

    private void establecerTablaPlatillos() {
        /// Crea los modelos para establecer en la tabla de platillos a vender
        Object[] columnas = {"Platillo", "Cantidad", "Costo"};
        Object[][] modelo = new Object[platillosAVender.size()][3];
        int x = 0;

        for (VentaPlatillo ventaPlatillo : platillosAVender) {

            modelo[x][0] = ventaPlatillo.getPlatillo().getNombre();
            modelo[x][1] = ventaPlatillo.getCantidad();
            modelo[x][2] = ventaPlatillo.getCosto();
            x++;
        }
        // Se establece el modelo en la tabla con los datos
        tablaPlatillosAVender.setDefaultEditor(Object.class, null);
        tablaPlatillosAVender.setModel(new DefaultTableModel(modelo, columnas));
        tablaPlatillosAVender.setCellSelectionEnabled(false);
        tablaPlatillosAVender.setRowSelectionAllowed(false);
        txtTotal.setText(total + "");
    }

    private ArrayList<Cliente> obtenerClientesActualizados(ArrayList<Cliente> lClientes) {
        ArrayList<Cliente> nuevaLista = new ArrayList(lClientes);
        if (lClientes.size() > 0) {
            int cantidad = (int) spinnercantidad.getValue();
            // Para cada elemento de los clientes ya seleccionados
            for (Cliente clienteSeleccionado : clientesSeleccionados) {
                int index = nuevaLista.indexOf(clienteSeleccionado);
                Cliente c = new Cliente(clienteSeleccionado);
                int valor = 0;
                switch (categoría) {
                    case "DESAYUNO": {
                        valor = c.getCreditoDesayuno() - creditosUsados.get(clientesSeleccionados.indexOf(c));
                        c.setCreditoDesayuno(valor);
                        break;
                    }

                    case "COMIDA": {
                        valor = nuevaLista.get(index).getCreditoComida() - creditosUsados.get(clientesSeleccionados.indexOf(clienteSeleccionado));
                        c.setCreditoComida(valor);
                        break;
                    }

                    case "CENA": {
                        valor = nuevaLista.get(index).getCreditoCena() - creditosUsados.get(clientesSeleccionados.indexOf(clienteSeleccionado));
                        c.setCreditoCena(valor);
                        break;
                    }

                }
                if (valor >= cantidad) {
                    nuevaLista.set(index, c);
                } else {
                    nuevaLista.remove(clienteSeleccionado);
                }
            }
        }
        return nuevaLista;
    }

    private void desactivarComponentes(Container componente) {
        for (Component c : componente.getComponents()) {
            if (c instanceof JPanel) {
                desactivarComponentes((JPanel) c);
            }
            c.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPlatillosAVender = new javax.swing.JTable();
        btnBorrarListaPlatillos = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCosto = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboPlatillos = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spinnercantidad = new javax.swing.JSpinner();
        checkBoxEsReserva = new javax.swing.JCheckBox();
        btnAgregarPlatillo = new javax.swing.JButton();
        checkBoxSopa = new javax.swing.JCheckBox();
        checkBoxParaLlevar = new javax.swing.JCheckBox();
        checkBoxCredito = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMenu = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        checkBoxTicket = new javax.swing.JCheckBox();
        btnRealizarVenta = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Platillos a vender");

        tablaPlatillosAVender.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Platillo", "Cantidad", "Costo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPlatillosAVender.setFocusable(false);
        tablaPlatillosAVender.setRowSelectionAllowed(false);
        tablaPlatillosAVender.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablaPlatillosAVender);
        if (tablaPlatillosAVender.getColumnModel().getColumnCount() > 0) {
            tablaPlatillosAVender.getColumnModel().getColumn(0).setResizable(false);
            tablaPlatillosAVender.getColumnModel().getColumn(1).setResizable(false);
            tablaPlatillosAVender.getColumnModel().getColumn(2).setResizable(false);
        }

        btnBorrarListaPlatillos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBorrarListaPlatillos.setText("Borrar todos");
        btnBorrarListaPlatillos.setEnabled(false);
        btnBorrarListaPlatillos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarListaPlatillosActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("TOTAL:   $");

        txtTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtTotal.setText("0.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnBorrarListaPlatillos, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal)
                                .addGap(13, 13, 13))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarListaPlatillos)
                    .addComponent(jLabel8)
                    .addComponent(txtTotal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtCosto.setModel(new javax.swing.SpinnerNumberModel(10.0f, 0.0f, null, 0.0f));
        txtCosto.setValue(0);
        txtCosto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtCostoStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("$");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("2. Indique el costo");

        comboPlatillos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-------SIN PLATILLOS DISPONIBLES------" }));
        comboPlatillos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPlatillosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("1. Seleccione el platillo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cantidad");

        spinnercantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinnercantidad.setEditor(new javax.swing.JSpinner.NumberEditor(spinnercantidad, ""));
        spinnercantidad.setValue(1);
        spinnercantidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnercantidadStateChanged(evt);
            }
        });

        checkBoxEsReserva.setText("Es reserva");
        checkBoxEsReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxEsReservaActionPerformed(evt);
            }
        });

        btnAgregarPlatillo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAgregarPlatillo.setText("AGREGAR");
        btnAgregarPlatillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlatilloActionPerformed(evt);
            }
        });

        checkBoxSopa.setText("Sopa");

        checkBoxParaLlevar.setText("¿Es para llevar?");

        checkBoxCredito.setText("Tiene crédito");
        checkBoxCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxCreditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboPlatillos, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinnercantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(checkBoxEsReserva))
                    .addComponent(jLabel2)
                    .addComponent(btnAgregarPlatillo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxSopa)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(5, 5, 5)
                        .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(checkBoxCredito))
                    .addComponent(checkBoxParaLlevar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPlatillos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnercantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxEsReserva))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxSopa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxParaLlevar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(checkBoxCredito))
                .addGap(18, 18, 18)
                .addComponent(btnAgregarPlatillo)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        tablaMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Platillo", "Reservados", "Para vender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMenu.setFocusable(false);
        tablaMenu.setRowSelectionAllowed(false);
        tablaMenu.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablaMenu);
        if (tablaMenu.getColumnModel().getColumnCount() > 0) {
            tablaMenu.getColumnModel().getColumn(0).setResizable(false);
            tablaMenu.getColumnModel().getColumn(1).setResizable(false);
            tablaMenu.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Menú de hoy");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("3. Nombre del cliente");

        txtNombreCliente.setEnabled(false);
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyReleased(evt);
            }
        });

        checkBoxTicket.setText("Imprimir ticket");
        checkBoxTicket.setEnabled(false);

        btnRealizarVenta.setText("REALIZAR VENTA");
        btnRealizarVenta.setEnabled(false);
        btnRealizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(checkBoxTicket)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRealizarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(219, 219, 219)
                .addComponent(jLabel6)
                .addGap(26, 26, 26)
                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(checkBoxTicket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRealizarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spinnercantidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnercantidadStateChanged
        
    }//GEN-LAST:event_spinnercantidadStateChanged

    private void txtCostoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtCostoStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoStateChanged

    private void btnAgregarPlatilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPlatilloActionPerformed
        Platillo platilloSeleccionado = (Platillo) comboPlatillos.getSelectedItem();
        int cantidad = (int) spinnercantidad.getValue();
        // Si previamente se seleccionó una reserva
        if (checkBoxEsReserva.isSelected()) {
            reservasSeleccionadas.add(modalSeleccionreserva.reservaSeleccionada);
            txtNombreCliente.setText(modalSeleccionreserva.reservaSeleccionada.getCliente().getNombre());
            // Habilita el campo para el nombre y la impresión del ticket
            txtNombreCliente.setEnabled(true);
            checkBoxTicket.setEnabled(true);
            btnRealizarVenta.setEnabled(true);
        }
        // si el cliente tiene crédito
        if (checkBoxCredito.isSelected()) {
            // Si el seleccionado ya habia seleccionado antes
            if (clientesSeleccionados.contains(modalSeleccionCliente.clienteSeleccionado)) {
                int index = clientesSeleccionados.indexOf(modalSeleccionCliente.clienteSeleccionado);
                Integer modificado = creditosUsados.get(index) + cantidad;
                creditosUsados.set(index, modificado);
            } else {
                clientesSeleccionados.add(modalSeleccionCliente.clienteSeleccionado);
                creditosUsados.add(cantidad);
            }
            txtNombreCliente.setText(modalSeleccionCliente.clienteSeleccionado.getNombre());
            // Habilita el campo para el nombre y la impresión del ticket
            txtNombreCliente.setEnabled(true);
            checkBoxTicket.setEnabled(true);
            btnRealizarVenta.setEnabled(true);

        }
        // Se crea una venta con el platillo seleccionado y se agrega a la lista
        int indiceVentaPlatillo = platillosAVender.size() + 1;
        float costo = (Float) txtCosto.getValue();
        total += costo;
        platillosAVender.add(new VentaPlatillo(indiceVentaPlatillo, null, "", platilloSeleccionado, cantidad, costo));
        // Si se seleccionó incluir sopa
        if (checkBoxSopa.isSelected()) {
            // Se crea una venta de sopa y se agrega a la lista
            platillosAVender.add(new VentaPlatillo(0, null, "", sopaDelDia, cantidad, 0f));
            indiceVentaPlatillo++;
        }
        // Se es para llevar
        if (checkBoxParaLlevar.isSelected()) {
            // Se indica el indice del platillo que es para llevar
            listaParaLLevar.add(indiceVentaPlatillo);
        }
        // Establece la tabla de platillos
        establecerTablaPlatillos();
        btnBorrarListaPlatillos.setEnabled(true);
        ///// Para actualizar el menú temporal
        PlatilloMenu p = null;
        // Se encuntra el platillo
        for (PlatilloMenu platilloMenu : listamenuCopia) {
            if (platilloMenu.getPlatillo().equals(platilloSeleccionado)) {
                p = new PlatilloMenu(platilloMenu.getIdPlatilloMenu(), platilloMenu.getPlatillo(), platilloMenu.getDiaSemana(), platilloMenu.getCantidad(), platilloMenu.getReservados(), platilloMenu.getCategoria());
                break;
            }
        }
        // Si era reserva
        if (checkBoxEsReserva.isSelected()) {
            // Se disminuyen los reservados del menú
            p.setReservados(p.getReservados() - cantidad);
        } else {
            p.setCantidad(p.getCantidad() - cantidad);
        }
        // Modifica el menú temporal
        listamenuCopia.set(listamenuCopia.indexOf(p), p);
        // restablece los campos para poder agregar más
        reestableceCamposPorAgregado();
        // Habilita el campo para el nombre y la impresión del ticket
        txtNombreCliente.setEnabled(true);
        // Actualiza el menú con la lista temporal
        establecerMenu(listamenuCopia);
    }//GEN-LAST:event_btnAgregarPlatilloActionPerformed

    private void comboPlatillosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPlatillosActionPerformed

//        Platillo p = (Platillo)comboPlatillos.getSelectedItem();
//        for(PlatilloMenu pm:listamenuCopia){
//            if(pm.getPlatillo().equals(p)){
//                if(pm.getCantidad()>pm.get)
//            }
//        }
    }//GEN-LAST:event_comboPlatillosActionPerformed

    private void checkBoxEsReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxEsReservaActionPerformed
        // Al dar click en el checkbox de reserva
        //Si fue seleccionado
        if (checkBoxEsReserva.isSelected()) {
            // Platillo seleccionado
            Platillo platillo = ((Platillo) comboPlatillos.getSelectedItem());
            int tipo = 0;
            switch (categoría) {
                case "DESAYUNO":
                    tipo = 1;
                    break;
                case "COMIDA":
                    tipo = 2;
                    break;
                case "CENA":
                    tipo = 3;
                    break;
            }
            // Se consultan las reservas para el día actual, el platillo seleccionado y el tipo
            ArrayList<ReservaPlatillo> lReservas = Control.reservas.consultarPorFechaPlatilloTipo(Calendar.getInstance().getTime(), platillo, tipo);
            // Se quitan de la lista todas las reservas seleccionadas para la misma venta
            lReservas.removeAll(this.reservasSeleccionadas);
            if (lReservas.size() > 0) {
                lanzarModalreservas(lReservas);
                // Si no contiene reservas
            } else {
                // Se muestra mensaje indicando que no hay
                JOptionPane.showMessageDialog(parent,
                        "No se han registrado reservas de " + platillo.getNombre() + " para hoy.",
                        "Imposible",
                        JOptionPane.INFORMATION_MESSAGE);
                // checkbox vuelve a lo normal
                checkBoxEsReserva.setSelected(false);
                if (platillosAVender.size() > 0) {
                    btnBorrarListaPlatillos.setEnabled(true);
                }

            }

        } else {
            reestableceCamposPorReserva();
        }
    }//GEN-LAST:event_checkBoxEsReservaActionPerformed
    private void reestableceTodo() {
        total = 0f;
        txtTotal.setText("0.00");
        txtNombreCliente.setText("");
        txtNombreCliente.setEnabled(false);
        checkBoxTicket.setSelected(false);
        checkBoxTicket.setEnabled(false);
        btnRealizarVenta.setEnabled(false);
// la lista de platillos a vender se elimina
        platillosAVender = new ArrayList<>();
        establecerTablaPlatillos();
        // Menú temporal se queda
        listaMenu = new ArrayList<>(listamenuCopia);
        establecerMenu(listaMenu);
        // Las reservas temporales y clientes temporales se eliminan
        reservasSeleccionadas = new ArrayList<>();
        clientesSeleccionados = new ArrayList<>();
        creditosUsados = new ArrayList<>();
        listaParaLLevar = new ArrayList<>();
        btnBorrarListaPlatillos.setEnabled(false);
    }
    private void btnBorrarListaPlatillosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarListaPlatillosActionPerformed
        total = 0f;
        txtTotal.setText("0.00");
        txtNombreCliente.setText("");
        txtNombreCliente.setEnabled(false);
        checkBoxTicket.setSelected(false);
        checkBoxTicket.setEnabled(false);
        btnRealizarVenta.setEnabled(false);
// la lista de platillos a vender se elimina
        platillosAVender = new ArrayList<>();
        establecerTablaPlatillos();
        // Menú temporal vuelve a ser como el original
        listamenuCopia = new ArrayList<>(listaMenu);
        establecerMenu(listaMenu);
        // Las reservas temporales y clientes temporales se eliminan
        reservasSeleccionadas = new ArrayList<>();
        clientesSeleccionados = new ArrayList<>();
        creditosUsados = new ArrayList<>();
        listaParaLLevar = new ArrayList<>();
        btnBorrarListaPlatillos.setEnabled(false);
    }//GEN-LAST:event_btnBorrarListaPlatillosActionPerformed

    private void checkBoxCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxCreditoActionPerformed
        // Al dar click en el checkbox de crédito
        //Si fue seleccionado
        int cantidad = (int) spinnercantidad.getValue();
        if (checkBoxCredito.isSelected()) {
            // Se consultan los clientes que tienen crédito para el horario seleccionado con la cantidad indicada como minimo
            ArrayList<Cliente> lClientes = Control.clientes.clientesConCreditoCategoria(categoría, cantidad - 1);
            // Si anteriormente se agregaron a la venta clientes con crédito
            if (clientesSeleccionados.size() > 0) {

                lClientes = obtenerClientesActualizados(lClientes);
            }
            if (lClientes.size() > 0) {

                lanzarModalClientes(lClientes);
                // Si no contiene clientes con crédito
            } else {
                // Se muestra mensaje indicando que no hay
                JOptionPane.showMessageDialog(parent,
                        "No se encontraron clientes con créditos de " + categoría.toLowerCase() + "s suficientes.",
                        "Imposible",
                        JOptionPane.INFORMATION_MESSAGE);
                // checkbox vuelve a lo normal
                checkBoxCredito.setSelected(false);
                if (platillosAVender.size() > 0) {
                    btnBorrarListaPlatillos.setEnabled(true);
                }
            }

        } else {
            checkBoxEsReserva.setEnabled(true);
            spinnercantidad.setEnabled(true);
            if (platillosAVender.size() > 0) {
                btnBorrarListaPlatillos.setEnabled(true);
            }
        }
    }//GEN-LAST:event_checkBoxCreditoActionPerformed

    private void txtNombreClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyReleased
        boolean tieneTexto = !txtNombreCliente.getText().equals("");
        checkBoxTicket.setEnabled(tieneTexto);
        btnRealizarVenta.setEnabled(tieneTexto);
    }//GEN-LAST:event_txtNombreClienteKeyReleased

    private void btnRealizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarVentaActionPerformed
        ConfirmacionVenta confir = new ConfirmacionVenta(parent, true, total);
        confir.setVisible(true);
        if (confir.aceptado) {
            // Generar venta general
            Calendar c = Calendar.getInstance();
            Venta v = new Venta(0, new Timestamp(c.getTimeInMillis()), Control.usuarioActivo);
            // Si se generó la venta
            if (Control.ventas.agregar(v)) {
                v = Control.ventas.consultarUltimaVenta();
                // Generar las ventas por platillo
                for (VentaPlatillo vp : platillosAVender) {
                    vp.setVentaGeneral(v);
                    vp.setNombreCliente(txtNombreCliente.getText());
                    Control.ventasPlatillos.agregarVentaPlatillo(vp);
                }
                // Ver si habia reservas y eliminarlas
                for (ReservaPlatillo reserva : reservasSeleccionadas) {
                    Control.reservas.eliminar(reserva.getIdReserva());
                }
                // Ver si habia creditos y actualizarlos
                int index = 0;
                for (Cliente cliente : clientesSeleccionados) {
                    switch (categoría) {
                        case "DESAYUNO":
                            cliente.setCreditoDesayuno(cliente.getCreditoDesayuno() - creditosUsados.get(index));
                            break;
                        case "COMIDA":
                            cliente.setCreditoComida(cliente.getCreditoComida() - creditosUsados.get(index));
                            break;
                        case "CENA":
                            cliente.setCreditoCena(cliente.getCreditoCena() - creditosUsados.get(index));
                            break;
                    }
                    Control.clientes.actualizar(cliente);
                    index++;
                }
                
                // Se encuntra el platillo
                index = 0;
                for (PlatilloMenu platilloMenu : listamenuCopia) {
                    int cantidadMenu = listaMenu.get(index).getCantidad();
                    int reservadosMenu = listaMenu.get(index).getReservados();
                    if (platilloMenu.getCantidad()<cantidadMenu || platilloMenu.getReservados()<reservadosMenu) {
                        Control.menu.actualizar(platilloMenu);
                    }
                    index++;
                }
                reestableceTodo();
                JOptionPane.showMessageDialog(parent, "Venta realizada!!!");
            }
        } else {
            System.out.println("Canceló");
        }
    }//GEN-LAST:event_btnRealizarVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPlatillo;
    private javax.swing.JButton btnBorrarListaPlatillos;
    private javax.swing.JButton btnRealizarVenta;
    private javax.swing.JCheckBox checkBoxCredito;
    private javax.swing.JCheckBox checkBoxEsReserva;
    private javax.swing.JCheckBox checkBoxParaLlevar;
    private javax.swing.JCheckBox checkBoxSopa;
    private javax.swing.JCheckBox checkBoxTicket;
    private javax.swing.JComboBox<String> comboPlatillos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spinnercantidad;
    private javax.swing.JTable tablaMenu;
    private javax.swing.JTable tablaPlatillosAVender;
    private javax.swing.JSpinner txtCosto;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables

}
