/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import logica.Control;
import negocio.Cliente;
import negocio.Platillo;
import negocio.PlatilloMenu;
import negocio.ReservaPlatillo;

/**
 *
 * @author JAB
 */
public class VReservasCliente extends javax.swing.JFrame {

    /**
     * Creates new form ProtoInterfazMagui
     */
    private Cliente clienteSeleccionado;
    private int diaSeleccinado;
    private Calendar fechaLunes;
    private int diaSemanaActual;
    private JButton[] arregloBotones = new JButton[8];

    public VReservasCliente() {

        initComponents();
        setLocationRelativeTo(null);
        // Establece el nombre del usuario
        this.labelNombreEmpleado.setText(Control.usuarioActivo.getNombre());
        // Obtiene los clientes que tienen credito
        Object[] clientes = Control.clientes.clientesParaModificar().toArray();

        // Si hay por lo menos un cliente con crédito
        if (clientes.length > 0) {
            // Los establece en el combo
            DefaultComboBoxModel modelo = new DefaultComboBoxModel(clientes);
            comboClientes.setModel(modelo);
            comboClientes.setEnabled(true);
            // Establece los datos del primero en la lista
            estableceDatosCliente();
            // Establece los datos del menú
            // Se calculan las fechas de lunes y domingo para establecerlas en el label de la semana
            fechaLunes = Calendar.getInstance();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es", "MX"));
            GregorianCalendar hoy = new GregorianCalendar(TimeZone.getTimeZone("GMT-7"),
                    Locale.FRENCH);
            //Se obtiene el día de la semana en base a Calendar(Domingo = 1, Lunes = 2, etc.)
            diaSemanaActual = hoy.get(Calendar.DAY_OF_WEEK);
            // Si el día de la semana es domingo
            if (diaSemanaActual == 1) {
                // Para el lunes se restan 6 días
                fechaLunes.add(Calendar.DAY_OF_YEAR, -6);
                // Se establece el dia real (Domingo = 7)
                diaSemanaActual = 7;
            } // Si es Lunes u otro
            else {
                // Se restan 2 más el actual 
                fechaLunes.add(Calendar.DAY_OF_YEAR, -(diaSemanaActual - 2));
                // Se establece el dia real
                diaSemanaActual--;
            }
            Calendar fechaBoton = Calendar.getInstance();
            fechaBoton.setTime(fechaLunes.getTime());
            /////// Botones del menú
            btnLunes.setText("<html><center><b>LUNES</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnLunes.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnMartes.setText("<html><center><b>MARTES</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnMartes.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnMiercoles.setText("<html><center><b>MIÉRCOLES</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnMiercoles.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnJueves.setText("<html><center><b>JUEVES</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnJueves.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnViernes.setText("<html><center><b>VIERNES</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnViernes.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnSabado.setText("<html><center><b>SABADO</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnSabado.setBackground(Color.GRAY);
            fechaBoton.add(Calendar.DAY_OF_YEAR, 1);
            btnDomingo.setText("<html><center><b>DOMINGO</b><br>" + formatoFecha.format(fechaBoton.getTime()) + "</center></html>");
            btnDomingo.setBackground(Color.GRAY);
// Arreglo de botones para facilitar su modificación
            arregloBotones[1] = btnLunes;
            arregloBotones[2] = btnMartes;
            arregloBotones[3] = btnMiercoles;
            arregloBotones[4] = btnJueves;
            arregloBotones[5] = btnViernes;
            arregloBotones[6] = btnSabado;
            arregloBotones[7] = btnDomingo;
            for (int i = 1; i < diaSemanaActual; i++) {
                // Todos lo botones menores al dia actual se deshabilitan pues es son días que ya pasaron.
                arregloBotones[i].setEnabled(false);
            }

            estableceMenu(diaSemanaActual);
            arregloBotones[diaSeleccinado].setBackground(Color.green);
            // Se agrega el evento a la tabla al seleccionar una reserva
            tableReservas.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = tableReservas.rowAtPoint(evt.getPoint());
                    int col = tableReservas.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col >= 0) {
                        Calendar fechaReserva = Calendar.getInstance();
                        fechaReserva.setTime(Control.reservas.consultarPorId((Integer) tableReservas.getValueAt(tableReservas.getSelectedRow(), 0)).getFecha());
                        Calendar fechaActual = Calendar.getInstance();
                        if (Control.usuarioActivo.isTipoAdmin()) {
                            btnEliminarReserva.setEnabled(fechaActual.get(Calendar.DAY_OF_YEAR) + 1 <= fechaReserva.get(Calendar.DAY_OF_YEAR));
                        } else {
                            btnEliminarReserva.setEnabled(true);
                        }

                    } else {
                        btnEliminarReserva.setEnabled(false);
                    }

                }
            });
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron clientes con crédito o reservas vigentes.", "Información", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
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

        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        labelNombreEmpleado = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboDesayunos = new javax.swing.JComboBox<>();
        spinnerDesayunos = new javax.swing.JSpinner();
        comboComidas = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        spinnerComidas = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        comboCenas = new javax.swing.JComboBox<>();
        spinnerCenas = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        btnReservar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReservas = new javax.swing.JTable();
        btnEliminarTodas = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        labelCreditoDesayunos = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelCreditoComidas = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelCreditoCenas = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLunes = new javax.swing.JButton();
        btnMartes = new javax.swing.JButton();
        btnMiercoles = new javax.swing.JButton();
        btnJueves = new javax.swing.JButton();
        btnViernes = new javax.swing.JButton();
        btnSabado = new javax.swing.JButton();
        btnDomingo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reservar platillo");
        setResizable(false);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Usuario: ");

        btnCancelar.setText("Aceptar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        labelNombreEmpleado.setText("<Nombre del empleado>");

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/768px-Back_Arrow.svg.png"))); // NOI18N
        btnVolver.setBorder(null);
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFocusable(false);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel1.setText("Desayunos:");

        jLabel3.setText("Cantidad:");

        comboDesayunos.setEnabled(false);
        comboDesayunos.setMaximumSize(new java.awt.Dimension(33, 26));
        comboDesayunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDesayunosActionPerformed(evt);
            }
        });

        spinnerDesayunos.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spinnerDesayunos.setEnabled(false);
        spinnerDesayunos.setValue(1);
        spinnerDesayunos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerDesayunosStateChanged(evt);
            }
        });

        comboComidas.setEnabled(false);

        jLabel12.setText("Comidas:");

        spinnerComidas.setEnabled(false);
        spinnerComidas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerComidasStateChanged(evt);
            }
        });

        jLabel2.setText("Cenas:");

        comboCenas.setEnabled(false);

        spinnerCenas.setEnabled(false);
        spinnerCenas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCenasStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("3. Reserva:");

        btnReservar.setText("Reservar");
        btnReservar.setEnabled(false);
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("3. Seleccione los platillos:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addGap(18, 18, 18)
                                            .addComponent(comboDesayunos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboComidas, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(comboCenas, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(spinnerComidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerCenas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerDesayunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboDesayunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(spinnerDesayunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboComidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(spinnerComidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboCenas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerCenas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(btnReservar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setText("Platillos reservados:");

        tableReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(10), "dfdfg", "sdfsdf", "2",  new Integer(1)},
                { new Integer(2), "0.1", "21.1", "1.212.", null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Fecha", "Platillo", "Tipo", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableReservas.setColumnSelectionAllowed(true);
        tableReservas.setFocusable(false);
        tableReservas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableReservas);
        tableReservas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableReservas.getColumnModel().getColumnCount() > 0) {
            tableReservas.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableReservas.getColumnModel().getColumn(4).setPreferredWidth(10);
        }

        btnEliminarTodas.setText("Eliminar todas");
        btnEliminarTodas.setEnabled(false);
        btnEliminarTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodasActionPerformed(evt);
            }
        });

        btnEliminarReserva.setText("Eliminar Seleccionada");
        btnEliminarReserva.setEnabled(false);
        btnEliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEliminarReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarTodas)
                    .addComponent(btnEliminarReserva))
                .addGap(35, 35, 35))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Cantidad de platillos que el cliente puede reservar ");

        jLabel14.setText("Desayunos:");

        labelCreditoDesayunos.setText("<Credito disponible>");

        jLabel15.setText("Comidas:");

        labelCreditoComidas.setText("<Credito disponible>");

        jLabel16.setText("Cenas:");

        labelCreditoCenas.setText("<Credito disponible>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCreditoDesayunos))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelCreditoCenas)
                                    .addComponent(labelCreditoComidas))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(labelCreditoDesayunos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(labelCreditoComidas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(labelCreditoCenas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Menú de la semana");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("2. Seleccione el día:");

        btnLunes.setLabel("LUNES");
        btnLunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLunesActionPerformed(evt);
            }
        });

        btnMartes.setText("MARTES");
        btnMartes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMartesActionPerformed(evt);
            }
        });

        btnMiercoles.setText("MIERCOLES");
        btnMiercoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiercolesActionPerformed(evt);
            }
        });

        btnJueves.setText("JUEVES");
        btnJueves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuevesActionPerformed(evt);
            }
        });

        btnViernes.setText("VIERNES");
        btnViernes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViernesActionPerformed(evt);
            }
        });

        btnSabado.setText("SÁBADO");
        btnSabado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSabadoActionPerformed(evt);
            }
        });

        btnDomingo.setText("DOMINGO");
        btnDomingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDomingoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnLunes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnMartes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnMiercoles, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnJueves, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnViernes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnSabado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnDomingo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLunes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMartes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMiercoles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJueves, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViernes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSabado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDomingo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("1. Seleccione el cliente:");

        comboClientes.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---NO EXISTEN CLIENTES CON CRÉDITO O RESERVAS---" }));
        comboClientes.setEnabled(false);
        comboClientes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboClientesItemStateChanged(evt);
            }
        });
        comboClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addGap(134, 134, 134)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 953, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(373, 373, 373)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNombreEmpleado)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolver)
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(labelNombreEmpleado))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es", "MX"));

        Platillo platillo;
        int desayunosCantidad = (Integer) spinnerDesayunos.getValue(),
                comidasCantidad = (Integer) spinnerComidas.getValue(),
                cenasCantidad = (Integer) spinnerCenas.getValue();
        Calendar fechaReserva = Calendar.getInstance();
        fechaReserva.setTime(fechaLunes.getTime());

        fechaReserva.add(Calendar.DAY_OF_YEAR, diaSeleccinado - 1);
        Object[] desayuno = null;
        Object[] comida = null;
        Object[] cena = null;
// Aquí se abrirá el cuadro de confirmación.

        if (desayunosCantidad > 0) {
            platillo = (Platillo) comboDesayunos.getSelectedItem();
            desayuno = new Object[2];
            desayuno[0] = platillo.getNombre();
            desayuno[1] = desayunosCantidad;

        }
        if (comidasCantidad > 0) {
            platillo = (Platillo) comboComidas.getSelectedItem();
            comida = new Object[2];
            comida[0] = platillo.getNombre();
            comida[1] = comidasCantidad;
        }
        if (cenasCantidad > 0) {
            platillo = (Platillo) comboCenas.getSelectedItem();
            cena = new Object[2];
            cena[0] = platillo.getNombre();
            cena[1] = cenasCantidad;
        }

        ConfirmarReserva dialog = new ConfirmarReserva(this, true, clienteSeleccionado.getNombre(), formatoFecha.format(fechaReserva.getTime()), desayuno, comida, cena);
        dialog.setVisible(true);
        if (dialog.getReturn() == 1) {
            if (reservar()) {
                estableceDatosCliente();
                arregloBotones[diaSeleccinado].setBackground(Color.gray);
                estableceMenu(diaSemanaActual);
                arregloBotones[diaSeleccinado].setBackground(Color.green);

            } else {
                JOptionPane.showMessageDialog(this, "Error: no se pudieron generar las reservas.");
            }
        }
    }//GEN-LAST:event_btnReservarActionPerformed

    private void comboDesayunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDesayunosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDesayunosActionPerformed

    private void comboClientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboClientesItemStateChanged
        // Si el evento fue de selección
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // Establece los datos del cliente seleccionado
            estableceDatosCliente();
            // Establece los datos del menu de acuerdo a los creditos del cliente
            estableceMenu(diaSemanaActual);

        }


    }//GEN-LAST:event_comboClientesItemStateChanged

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void comboClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboClientesActionPerformed

    private void spinnerDesayunosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerDesayunosStateChanged
        comprobarCantidades();
    }//GEN-LAST:event_spinnerDesayunosStateChanged

    private void spinnerComidasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerComidasStateChanged
        comprobarCantidades();
    }//GEN-LAST:event_spinnerComidasStateChanged

    private void spinnerCenasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCenasStateChanged
        comprobarCantidades();
    }//GEN-LAST:event_spinnerCenasStateChanged

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodasActionPerformed
        boolean noDisponible = false;
        boolean eliminado = false;
        int nReservas = tableReservas.getModel().getRowCount();

        for (int i = 0; i < nReservas; i++) {
            int idReserva = (int) tableReservas.getValueAt(i, 0);
            String tipo = tableReservas.getValueAt(i, 3).toString();
            int cantidad = (int) tableReservas.getValueAt(i, 4);
            Calendar fechaReserva = Calendar.getInstance();
            fechaReserva.setTime(Control.reservas.consultarPorId(idReserva).getFecha());
            Calendar fechaActual = Calendar.getInstance();
            if (!Control.usuarioActivo.isTipoAdmin() && fechaActual.get(Calendar.DAY_OF_YEAR) + 1 > fechaReserva.get(Calendar.DAY_OF_YEAR) ) {
                noDisponible =true;
                
            } else {
                Control.reservas.eliminar(idReserva);
                switch (tipo) {

                    case "DESAYUNO":
                        clienteSeleccionado.setCreditoDesayuno(clienteSeleccionado.getCreditoDesayuno() + cantidad);
                        break;
                    case "COMIDA":
                        clienteSeleccionado.setCreditoComida(clienteSeleccionado.getCreditoComida() + cantidad);
                        break;
                    case "CENA":
                        clienteSeleccionado.setCreditoCena(clienteSeleccionado.getCreditoCena() + cantidad);
                        break;
                }
                Control.clientes.actualizar(clienteSeleccionado);
                eliminado = true;
            }

        }
if(eliminado){
        estableceDatosCliente();
        estableceMenu(diaSeleccinado);
}
if(noDisponible){
    JOptionPane.showMessageDialog(this, "Una o más reservas no se pudieron eliminar pues se necesita por lo menos un día de antelación.","Atención", JOptionPane.INFORMATION_MESSAGE);
}
    }//GEN-LAST:event_btnEliminarTodasActionPerformed

    private void btnLunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLunesActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(1);
        arregloBotones[1].setBackground(Color.green);


    }//GEN-LAST:event_btnLunesActionPerformed

    private void btnMartesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMartesActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(2);
        arregloBotones[2].setBackground(Color.green);

    }//GEN-LAST:event_btnMartesActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        int idReserva = (int) tableReservas.getValueAt(tableReservas.getSelectedRow(), 0);
        String tipo = tableReservas.getValueAt(tableReservas.getSelectedRow(), 3).toString();
        int cantidad = (int) tableReservas.getValueAt(tableReservas.getSelectedRow(), 4);

        Control.reservas.eliminar(idReserva);
        switch (tipo) {
            case "DESAYUNO":
                clienteSeleccionado.setCreditoDesayuno(clienteSeleccionado.getCreditoDesayuno() + cantidad);
                break;
            case "COMIDA":
                clienteSeleccionado.setCreditoComida(clienteSeleccionado.getCreditoComida() + cantidad);
                break;
            case "CENA":
                clienteSeleccionado.setCreditoCena(clienteSeleccionado.getCreditoCena() + cantidad);
                break;
        }
        Control.clientes.actualizar(clienteSeleccionado);
        estableceDatosCliente();
        estableceMenu(diaSeleccinado);
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void btnMiercolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiercolesActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(3);
        arregloBotones[3].setBackground(Color.green);
    }//GEN-LAST:event_btnMiercolesActionPerformed

    private void btnJuevesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuevesActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(4);
        arregloBotones[4].setBackground(Color.green);    }//GEN-LAST:event_btnJuevesActionPerformed

    private void btnViernesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViernesActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(5);
        arregloBotones[5].setBackground(Color.green);    }//GEN-LAST:event_btnViernesActionPerformed

    private void btnSabadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSabadoActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(6);
        arregloBotones[6].setBackground(Color.green);
    }//GEN-LAST:event_btnSabadoActionPerformed

    private void btnDomingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDomingoActionPerformed
        arregloBotones[diaSeleccinado].setBackground(Color.gray);
        estableceMenu(7);
        arregloBotones[7].setBackground(Color.green);    }//GEN-LAST:event_btnDomingoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VReservasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VReservasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VReservasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VReservasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VReservasCliente().setVisible(true);
            }
        });
    }

    private void estableceDatosCliente() {
        // Obtiene el cliente selecionado
        clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
        // Obtiene y muestra los créditos
        int creditoDesayuno = clienteSeleccionado.getCreditoDesayuno();
        int creditoComida = clienteSeleccionado.getCreditoComida();
        int creditoCena = clienteSeleccionado.getCreditoCena();
        labelCreditoDesayunos.setText(creditoDesayuno + "");
        labelCreditoComidas.setText(creditoComida + "");
        labelCreditoCenas.setText(creditoCena + "");
        btnReservar.setEnabled(false);
        // Si existen creditos de desayuno
        if (creditoDesayuno > 0) {
            // Habilita el combo de desayunos y el spinner
            comboDesayunos.setEnabled(true);
            spinnerDesayunos.setEnabled(true);
            spinnerDesayunos.setModel(new SpinnerNumberModel(0, 0, creditoDesayuno, 1));
        } else {
            // Deshabilita el combo de desayunos y el spinner
            comboDesayunos.setEnabled(false);
            spinnerDesayunos.setEnabled(false);
            spinnerDesayunos.setModel(new SpinnerNumberModel(0, 0, creditoDesayuno, 1));
        }
        // Si existen creditos de comida
        if (creditoComida > 0) {
            // Habilita el combo de comidas y el spinner
            comboComidas.setEnabled(true);
            spinnerComidas.setEnabled(true);
            spinnerComidas.setModel(new SpinnerNumberModel(0, 0, creditoComida, 1));
        } else {
            // Deshabilita el combo de comidas y el spinner
            comboComidas.setEnabled(false);
            spinnerComidas.setEnabled(false);
            spinnerComidas.setModel(new SpinnerNumberModel(0, 0, creditoComida, 1));
        }

        // Si existen creditos de cena
        if (creditoCena > 0) {
            // Habilita el combo de comidas y el spinner
            comboCenas.setEnabled(true);
            spinnerCenas.setEnabled(true);
            spinnerCenas.setModel(new SpinnerNumberModel(0, 0, creditoCena, 1));
        } else {
            // Deshabilita el combo de cenas y el spinner
            comboCenas.setEnabled(false);
            spinnerCenas.setEnabled(false);
            spinnerCenas.setModel(new SpinnerNumberModel(0, 0, creditoCena, 1));
        }
        ArrayList<ReservaPlatillo> reservasCliente = Control.reservas.consultarReservasClienteVigente(clienteSeleccionado, Calendar.getInstance().getTime());
        Object[] columnas = {"No.", "Fecha", "Platillo", "Tipo", "Cantidad"};
        Object[][] modelo = new Object[reservasCliente.size()][5];
        int x = 0;
        String tipoReserva;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es", "MX"));
        for (ReservaPlatillo reserva : reservasCliente) {
            tipoReserva = "DESAYUNO";
            modelo[x][0] = reserva.getIdReserva();
            modelo[x][1] = formatoFecha.format(reserva.getFecha());
            modelo[x][2] = reserva.getPlatillo();
            if (reserva.getTipo() == 1) {
                tipoReserva = "DESAYUNO";
            } else {
                if (reserva.getTipo() == 2) {
                    tipoReserva = "COMIDA";
                } else {
                    tipoReserva = "CENA";
                }
            }
            modelo[x][3] = tipoReserva;
            modelo[x][4] = reserva.getCantidad();
            x++;
        }
        tableReservas.setDefaultEditor(Object.class, null);
        tableReservas.setModel(new DefaultTableModel(modelo, columnas));
        tableReservas.setCellSelectionEnabled(false);
        tableReservas.setRowSelectionAllowed(true);
        tableReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableReservas.setSelectionBackground(Color.lightGray);
        btnEliminarReserva.setEnabled(false);
        btnEliminarTodas.setEnabled(reservasCliente.size() > 0);
    }

    private void estableceMenu(int diaSeleccionado) {
        this.diaSeleccinado = diaSeleccionado;
        ArrayList<Platillo> platillosDesayunos = new ArrayList<>();
        ArrayList<Platillo> platillosComidas = new ArrayList<>();
        ArrayList<Platillo> platillosCenas = new ArrayList<>();
        for (PlatilloMenu platilloMenu : Control.menu.consultarMenuDia(diaSeleccionado)) {
            switch (platilloMenu.getCategoria()) {
                case "DESAYUNO":
                    platillosDesayunos.add(platilloMenu.getPlatillo());
                    break;
                case "COMIDA":
                    platillosComidas.add(platilloMenu.getPlatillo());
                    break;
                case "CENA":
                    platillosCenas.add(platilloMenu.getPlatillo());
                    break;
            }
        }
        if (platillosDesayunos.size() > 0) {
            comboDesayunos.setModel(new DefaultComboBoxModel(platillosDesayunos.toArray()));
            if (clienteSeleccionado.getCreditoDesayuno() > 0) {

                comboDesayunos.setEnabled(true);
                spinnerDesayunos.setEnabled(true);
            }
        } else {

            comboDesayunos.setModel(new DefaultComboBoxModel<>());
            comboDesayunos.addItem("---NO HAY MENÚ---");
            comboDesayunos.setEnabled(false);
            spinnerDesayunos.setEnabled(false);
        }

        if (platillosComidas.size() > 0) {
            comboComidas.setModel(new DefaultComboBoxModel(platillosComidas.toArray()));
            if (clienteSeleccionado.getCreditoComida() > 0) {

                comboComidas.setEnabled(true);
                spinnerComidas.setEnabled(true);
            }
        } else {

            comboComidas.setModel(new DefaultComboBoxModel<>());
            comboComidas.addItem("---NO HAY MENÚ---");
            comboComidas.setEnabled(false);
            spinnerComidas.setEnabled(false);
        }

        if (platillosCenas.size() > 0) {
            comboCenas.setModel(new DefaultComboBoxModel(platillosCenas.toArray()));
            if (clienteSeleccionado.getCreditoCena() > 0) {

                comboCenas.setEnabled(true);
                spinnerCenas.setEnabled(true);
            }
        } else {

            comboCenas.setModel(new DefaultComboBoxModel<>());
            comboCenas.addItem("---NO HAY MENÚ---");
            comboCenas.setEnabled(false);
            spinnerCenas.setEnabled(false);
        }
        spinnerDesayunos.setValue(0);
        spinnerComidas.setValue(0);
        spinnerCenas.setValue(0);

    }

    public void comprobarCantidades() {
        int seleccionDesayunos = (Integer) spinnerDesayunos.getValue();
        int seleccionComidas = (Integer) spinnerComidas.getValue();
        int seleccionCenas = (Integer) spinnerCenas.getValue();
        boolean condicion = seleccionDesayunos + seleccionComidas + seleccionCenas > 0;
        btnReservar.setEnabled(condicion);
    }

    public boolean reservar() {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es", "MX"));
        Cliente copia = clienteSeleccionado;
        Platillo platillo;
        int desayunosCantidad = (Integer) spinnerDesayunos.getValue(),
                comidasCantidad = (Integer) spinnerComidas.getValue(),
                cenasCantidad = (Integer) spinnerCenas.getValue();
        Calendar fechaReserva = Calendar.getInstance();
        fechaReserva.setTime(fechaLunes.getTime());

        fechaReserva.add(Calendar.DAY_OF_YEAR, diaSeleccinado - 1);

        if (desayunosCantidad > 0) {
            platillo = (Platillo) comboDesayunos.getSelectedItem();
            if (!Control.reservas.agregar(new ReservaPlatillo(0, clienteSeleccionado, platillo, desayunosCantidad, fechaReserva.getTime(), 1))) {
                return false;
            }
            copia.setCreditoDesayuno(copia.getCreditoDesayuno() - desayunosCantidad);
        }
        if (comidasCantidad > 0) {
            platillo = (Platillo) comboComidas.getSelectedItem();
            if (!Control.reservas.agregar(new ReservaPlatillo(0, clienteSeleccionado, platillo, comidasCantidad, fechaReserva.getTime(), 2))) {
                return false;
            }
            copia.setCreditoComida(copia.getCreditoComida() - comidasCantidad);
        }
        if (cenasCantidad > 0) {
            platillo = (Platillo) comboCenas.getSelectedItem();
            if (!Control.reservas.agregar(new ReservaPlatillo(0, clienteSeleccionado, platillo, cenasCantidad, fechaReserva.getTime(), 3))) {
                return false;
            }
            copia.setCreditoCena(copia.getCreditoCena() - cenasCantidad);
        }
        if (!Control.clientes.actualizar(clienteSeleccionado)) {
            return false;
        }
        clienteSeleccionado = copia;
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDomingo;
    private javax.swing.JButton btnEliminarReserva;
    private javax.swing.JButton btnEliminarTodas;
    private javax.swing.JButton btnJueves;
    private javax.swing.JButton btnLunes;
    private javax.swing.JButton btnMartes;
    private javax.swing.JButton btnMiercoles;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnSabado;
    private javax.swing.JButton btnViernes;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboCenas;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboComidas;
    private javax.swing.JComboBox<String> comboDesayunos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel labelCreditoCenas;
    private javax.swing.JLabel labelCreditoComidas;
    private javax.swing.JLabel labelCreditoDesayunos;
    private javax.swing.JLabel labelNombreEmpleado;
    private javax.swing.JSpinner spinnerCenas;
    private javax.swing.JSpinner spinnerComidas;
    private javax.swing.JSpinner spinnerDesayunos;
    private javax.swing.JTable tableReservas;
    // End of variables declaration//GEN-END:variables

}
