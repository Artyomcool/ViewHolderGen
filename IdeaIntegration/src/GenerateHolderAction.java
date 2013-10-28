import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.ui.SelectFromListDialog;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackage;
import org.artyomcool.viewholdergen.Model;
import org.artyomcool.viewholdergen.ModelWriter;
import org.artyomcool.viewholdergen.XmlParser;
import org.jetbrains.android.facet.AndroidFacet;

import javax.swing.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class GenerateHolderAction extends AnAction {

    private static String NOTIFICATION_ID = "GenerateHolder";

    private XmlParser parser = new XmlParser();

    public void actionPerformed(AnActionEvent e) {
        Notifications.Bus.register(NOTIFICATION_ID, NotificationDisplayType.BALLOON);
        Module module = e.getData(DataKeys.MODULE);
        PsiFile file = e.getData(DataKeys.PSI_FILE);

        assert module != null;
        assert file != null;

        AndroidFacet facet = AndroidFacet.getInstance(module);
        String packageName = facet.getManifest().getPackage().getValue();

        PackageChooserDialog dialog = new PackageChooserDialog("Select a package", module);
        if (dialog.showAndGet()) {
            PsiDirectory[] directories = dialog.getSelectedPackage().getDirectories();
            if (directories.length > 1) {
                SelectFromListDialog selectDialog = new SelectFromListDialog(module.getProject(), directories, new SelectFromListDialog.ToStringAspect() {
                    @Override
                    public String getToStirng(Object o) {
                        return ((PsiDirectory)o).getVirtualFile().getPath();
                    }
                }, "Choose directory", ListSelectionModel.SINGLE_SELECTION);
                if (selectDialog.showAndGet()) {
                    PsiDirectory directory = (PsiDirectory) selectDialog.getSelection()[0];
                    generate(file.getVirtualFile(), packageName, dialog.getSelectedPackage(), directory);
                }
            } else {
                generate(file.getVirtualFile(), packageName, dialog.getSelectedPackage(), directories[0]);
            }
        }
    }

    private void generate(final VirtualFile file, final String basePackage, final PsiPackage psiPackage,
                          final PsiDirectory directory) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                generateInternal(file, basePackage, psiPackage, directory);
            }
        });
    }

    private void generateInternal(VirtualFile file, String basePackage, PsiPackage psiPackage, PsiDirectory directory) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;

        try {
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                showNotification("Can't open layout file");
                return;
            }

            Model model;
            try {
                model = parser.parse(inputStream, basePackage, psiPackage.getQualifiedName(), file.getNameWithoutExtension());
            } catch (IOException e) {
                showNotification("Can't parse layout file");
                return;
            }

            PsiFile f = directory.createFile(model.getClassName()+".java");
            try {
                outputStream = f.getVirtualFile().getOutputStream(this);
            } catch (IOException e) {
                showNotification("Can't open " + model.getClassName() + ".java");
                return;
            }
            try {
                writer = new OutputStreamWriter(outputStream);
                new ModelWriter(model).write(writer);
            } catch (IOException e) {
                showNotification("Can't generate ViewHolder");
                e.printStackTrace();
                return;
            }
        } finally {
            close(inputStream, writer, outputStream);
        }
    }

    private void close(Closeable... closeable) {
        for (Closeable c : closeable) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void showNotification(String notification) {
        Notifications.Bus.notify(new Notification(NOTIFICATION_ID, "Error", notification, NotificationType.ERROR));
    }

    @Override
    public void update(AnActionEvent e) {
        PsiFile file = e.getData(DataKeys.PSI_FILE);
        if (file == null) {
            e.getPresentation().setVisible(false);
            return;
        }
        VirtualFile f = file.getVirtualFile();
        e.getPresentation().setVisible(f.getParent().getName().startsWith("layout"));
    }
}
