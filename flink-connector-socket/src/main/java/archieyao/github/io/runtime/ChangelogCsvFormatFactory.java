package archieyao.github.io.runtime;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DeserializationFormatFactory;
import org.apache.flink.table.factories.DynamicTableFactory;
import org.apache.flink.table.factories.FactoryUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** @author ArchieYao create at 2022/4/10 4:06 PM */
public class ChangelogCsvFormatFactory implements DeserializationFormatFactory {

    public static final ConfigOption<String> COLUMN_DELIMITER =
            ConfigOptions.key("column-delimiter").stringType().defaultValue("|");

    @Override
    public String factoryIdentifier() {
        return "changelog-csv";
    }

    @Override
    public DecodingFormat<DeserializationSchema<RowData>> createDecodingFormat(
            DynamicTableFactory.Context context, ReadableConfig readableConfig) {
        FactoryUtil.validateFactoryOptions(this, readableConfig);
        final String columnDelimiter = readableConfig.get(COLUMN_DELIMITER);
        return new ChangelogCsvFormat(columnDelimiter);
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return Collections.emptySet();
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(COLUMN_DELIMITER);
        return options;
    }
}
