import java.io.*;
import java.util.*;


public class RemoveAnnotation {
    static String inputFile = "D:\\future";
    static String outputFile = "D:\\future_01";

    // 支持的文件类型和对应的注释规则
    private static final Map<String, FileType> FILE_TYPES = new HashMap<>();

    static {
        // Java, JavaScript, TypeScript, C, C++, C#, etc.
        FILE_TYPES.put("java", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("js", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("ts", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("cpp", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("c", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("cs", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("php", new FileType("//", "/*", "*/", "\"", "'"));

        // CSS, SCSS, Less
        FILE_TYPES.put("css", new FileType(null, "/*", "*/", null, null));
        FILE_TYPES.put("scss", new FileType("//", "/*", "*/", "\"", "'"));
        FILE_TYPES.put("less", new FileType("//", "/*", "*/", "\"", "'"));

        // HTML, XML
        FILE_TYPES.put("html", new FileType(null, "<!--", "-->", "\"", "'"));
        FILE_TYPES.put("htm", new FileType(null, "<!--", "-->", "\"", "'"));
        FILE_TYPES.put("xml", new FileType(null, "<!--", "-->", "\"", "'"));

        // Vue files (mixed content)
        FILE_TYPES.put("vue", new FileType("//", "/*", "*/", "\"", "'"));

        // Python, Ruby, Perl
        FILE_TYPES.put("py", new FileType("#", null, null, "\"", "'"));
        FILE_TYPES.put("rb", new FileType("#", null, null, "\"", "'"));
        FILE_TYPES.put("pl", new FileType("#", null, null, "\"", "'"));

        // Shell scripts
        FILE_TYPES.put("sh", new FileType("#", null, null, "\"", "'"));
        FILE_TYPES.put("bash", new FileType("#", null, null, "\"", "'"));

        // Configuration files
        FILE_TYPES.put("properties", new FileType("#", null, null, null, null));
        FILE_TYPES.put("yml", new FileType("#", null, null, "\"", "'"));
        FILE_TYPES.put("yaml", new FileType("#", null, null, "\"", "'"));
    }

    public static void main(String[] args) {
        if (args.length >= 2) {
            inputFile = args[0];
            outputFile = args[1];
        }
        RemoveAnnotation me = new RemoveAnnotation();

        File input = new File(inputFile);
        File output = new File(outputFile);

        try {
            if (input.isDirectory()) {
                me.processDirectory(input, output);
            } else {
                me.handleFile(input, output);
            }
            System.out.println("注释删除完成！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 文件类型定义
     */
    static class FileType {
        String singleLineComment;
        String multiLineCommentStart;
        String multiLineCommentEnd;
        String stringDelimiter1;
        String stringDelimiter2;

        public FileType(String singleLineComment, String multiLineCommentStart,
                        String multiLineCommentEnd, String stringDelimiter1, String stringDelimiter2) {
            this.singleLineComment = singleLineComment;
            this.multiLineCommentStart = multiLineCommentStart;
            this.multiLineCommentEnd = multiLineCommentEnd;
            this.stringDelimiter1 = stringDelimiter1;
            this.stringDelimiter2 = stringDelimiter2;
        }

        public boolean hasSingleLineComment() {
            return singleLineComment != null && !singleLineComment.isEmpty();
        }

        public boolean hasMultiLineComment() {
            return multiLineCommentStart != null && multiLineCommentEnd != null;
        }

        public boolean hasStrings() {
            return stringDelimiter1 != null || stringDelimiter2 != null;
        }

        public boolean shouldProcess() {
            return hasSingleLineComment() || hasMultiLineComment();
        }
    }

    /**
     * 根据文件扩展名获取文件类型
     */
    private FileType getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            String extension = fileName.substring(dotIndex + 1).toLowerCase();
            return FILE_TYPES.getOrDefault(extension,
                    new FileType(null, null, null, null, null));
        }
        return new FileType(null, null, null, null, null);
    }

    /**
     * 处理文件
     */
    public void handleFile(File in, File out) throws Exception {
        FileType fileType = getFileType(in);

        if (!fileType.shouldProcess()) {
            copyFile(in, out);
            return;
        }

        if ("vue".equalsIgnoreCase(getFileExtension(in))) {
            handleVueFile(in, out);
        } else {
            removeCommentsAndEmptyLinesFromFile(in, out, fileType);
        }
    }

    /**
     * 使用基于行的方法移除注释和空行
     */
    private void removeCommentsAndEmptyLinesFromFile(File in, File out, FileType fileType) throws Exception {
        List<String> lines = readFileLines(in);
        List<String> processedLines = processLines(lines, fileType);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            for (String line : processedLines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    /**
     * 读取文件内容到行列表
     */
    private List<String> readFileLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * 处理行，移除注释和空行
     */
    private List<String> processLines(List<String> lines, FileType fileType) {
        List<String> result = new ArrayList<>();
        boolean inBlockComment = false;

        for (String line : lines) {
            String processedLine = line;

            // 检查是否是空行（只包含空白字符的行）
            if (isBlankLine(processedLine)) {
                continue; // 跳过空行
            }

            if (inBlockComment) {
                // 在多行注释中，检查是否结束
                int endIndex = processedLine.indexOf(fileType.multiLineCommentEnd);
                if (endIndex != -1) {
                    // 多行注释结束
                    processedLine = processedLine.substring(endIndex + fileType.multiLineCommentEnd.length());
                    inBlockComment = false;
                    // 如果注释后的行不为空，添加到结果
                    if (!isBlankLine(processedLine)) {
                        result.add(processedLine);
                    }
                }
                // 否则，整行都在注释中，跳过
                continue;
            }

            // 处理多行注释开始
            if (fileType.hasMultiLineComment()) {
                int blockStartIndex = processedLine.indexOf(fileType.multiLineCommentStart);
                while (blockStartIndex != -1) {
                    // 检查是否在字符串中
                    if (!isInString(processedLine, blockStartIndex, fileType)) {
                        int blockEndIndex = processedLine.indexOf(fileType.multiLineCommentEnd, blockStartIndex + fileType.multiLineCommentStart.length());
                        if (blockEndIndex != -1) {
                            // 多行注释在同一行开始和结束
                            processedLine = processedLine.substring(0, blockStartIndex) +
                                    processedLine.substring(blockEndIndex + fileType.multiLineCommentEnd.length());
                        } else {
                            // 多行注释跨行
                            processedLine = processedLine.substring(0, blockStartIndex);
                            inBlockComment = true;
                            break;
                        }
                    }
                    blockStartIndex = processedLine.indexOf(fileType.multiLineCommentStart, blockStartIndex + 1);
                }
            }

            // 处理单行注释
            if (!inBlockComment && fileType.hasSingleLineComment()) {
                int singleLineIndex = processedLine.indexOf(fileType.singleLineComment);
                while (singleLineIndex != -1) {
                    // 检查是否在字符串中
                    if (!isInString(processedLine, singleLineIndex, fileType)) {
                        processedLine = processedLine.substring(0, singleLineIndex);
                        break;
                    }
                    singleLineIndex = processedLine.indexOf(fileType.singleLineComment, singleLineIndex + 1);
                }
            }

            // 检查处理后的行是否为空
            if (!isBlankLine(processedLine)) {
                result.add(processedLine);
            }
        }

        return result;
    }

    /**
     * 检查是否是空白行（只包含空白字符的行）
     */
    private boolean isBlankLine(String line) {
        if (line == null || line.isEmpty()) {
            return true;
        }
        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定位置是否在字符串中
     */
    private boolean isInString(String line, int position, FileType fileType) {
        boolean inSingleQuoteString = false;
        boolean inDoubleQuoteString = false;
        boolean escaped = false;

        for (int i = 0; i < position; i++) {
            char c = line.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (fileType.stringDelimiter1 != null && fileType.stringDelimiter1.length() == 1 &&
                    c == fileType.stringDelimiter1.charAt(0) && !inSingleQuoteString) {
                inDoubleQuoteString = !inDoubleQuoteString;
            }

            if (fileType.stringDelimiter2 != null && fileType.stringDelimiter2.length() == 1 &&
                    c == fileType.stringDelimiter2.charAt(0) && !inDoubleQuoteString) {
                inSingleQuoteString = !inSingleQuoteString;
            }
        }

        return inSingleQuoteString || inDoubleQuoteString;
    }

    /**
     * 处理Vue文件的特殊逻辑
     */
    private void handleVueFile(File in, File out) throws Exception {
        List<String> lines = readFileLines(in);
        List<String> resultLines = new ArrayList<>();

        boolean inTemplate = false;
        boolean inScript = false;
        boolean inStyle = false;

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.startsWith("<template>")) {
                inTemplate = true;
                inScript = false;
                inStyle = false;
                resultLines.add(line);
            } else if (trimmed.startsWith("</template>")) {
                inTemplate = false;
                resultLines.add(line);
            } else if (trimmed.startsWith("<script>")) {
                inScript = true;
                inTemplate = false;
                inStyle = false;
                resultLines.add(line);
            } else if (trimmed.startsWith("</script>")) {
                inScript = false;
                resultLines.add(line);
            } else if (trimmed.startsWith("<style>")) {
                inStyle = true;
                inTemplate = false;
                inScript = false;
                resultLines.add(line);
            } else if (trimmed.startsWith("</style>")) {
                inStyle = false;
                resultLines.add(line);
            } else if (inTemplate) {
                // HTML内容 - 移除HTML注释
                String processed = removeHtmlComments(line);
                if (!isBlankLine(processed)) {
                    resultLines.add(processed);
                }
            } else if (inScript) {
                // JavaScript内容 - 移除JS注释
                List<String> singleLine = Arrays.asList(line);
                List<String> processed = processLines(singleLine, new FileType("//", "/*", "*/", "\"", "'"));
                if (!processed.isEmpty()) {
                    resultLines.add(processed.get(0));
                }
            } else if (inStyle) {
                // CSS内容 - 移除CSS注释
                List<String> singleLine = Arrays.asList(line);
                List<String> processed = processLines(singleLine, new FileType(null, "/*", "*/", null, null));
                if (!processed.isEmpty()) {
                    resultLines.add(processed.get(0));
                }
            } else {
                // 其他内容
                if (!isBlankLine(line)) {
                    resultLines.add(line);
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            for (String line : resultLines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    private String removeHtmlComments(String line) {
        // 简单的HTML注释移除
        return line.replaceAll("<!--.*?-->", "");
    }

    /**
     * 递归处理目录中的所有支持的文件
     */
    public void processDirectory(File inputDir, File outputDir) throws Exception {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        List<File> allFiles = getAllSupportedFiles(inputDir);

        for (File file : allFiles) {
            String relativePath = getRelativePath(inputDir, file);
            File outputFile = new File(outputDir, relativePath);

            outputFile.getParentFile().mkdirs();

            FileType fileType = getFileType(file);

            System.out.println("处理文件: " + file.getAbsolutePath() +
                    " (类型: " + getFileExtension(file) + ")");

            if (!fileType.shouldProcess()) {
                copyFile(file, outputFile);
                continue;
            }

            handleFile(file, outputFile);
        }
    }

    /**
     * 直接复制文件
     */
    private void copyFile(File source, File dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1).toLowerCase();
    }

    /**
     * 获取所有支持的文件
     */
    private List<File> getAllSupportedFiles(File dir) {
        List<File> supportedFiles = new ArrayList<>();
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 跳过一些常见的无关目录
                    String dirName = file.getName().toLowerCase();
                    if (dirName.equals(".git") || dirName.equals(".svn") || dirName.equals("node_modules")) {
                        continue;
                    }
                    supportedFiles.addAll(getAllSupportedFiles(file));
                } else {
                    String ext = getFileExtension(file);
                    if (FILE_TYPES.containsKey(ext) || isVueLikeFile(file)) {
                        supportedFiles.add(file);
                    }
                }
            }
        }
        return supportedFiles;
    }

    /**
     * 检查是否是Vue类文件
     */
    private boolean isVueLikeFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".vue") || name.endsWith(".svelte") || name.endsWith(".astro");
    }

    /**
     * 获取相对路径
     */
    private String getRelativePath(File baseDir, File file) {
        String basePath = baseDir.getAbsolutePath();
        String filePath = file.getAbsolutePath();

        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length() + 1);
        }
        return file.getName();
    }
}