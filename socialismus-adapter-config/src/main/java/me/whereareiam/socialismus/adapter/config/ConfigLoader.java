package me.whereareiam.socialismus.adapter.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.name.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConfigLoader<T> {
	private final Path dataPath;
	private final Gson gson;
	@Getter
	@Setter
	private T config;

	public ConfigLoader(@Named("dataPath") Path dataPath) {
		this.dataPath = dataPath;
		this.gson = new GsonBuilder()
				.setPrettyPrinting()
				.disableHtmlEscaping()
				.create();
	}

	public void load(Class<T> configClass, String path, String fileName) {
		File file;
		if (path.isEmpty()) {
			file = dataPath.resolve(fileName).toFile();
		} else {
			Path filePath = dataPath.resolve(path).resolve(fileName);
			try {
				Files.createDirectories(filePath.getParent());
				file = filePath.toFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (!file.exists() || file.length() == 0) {
			try {
				boolean fileCreated = file.createNewFile();
				if (fileCreated) {
					config = configClass.getDeclaredConstructor().newInstance();
					save(path, fileName);
				}
			} catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException |
			         InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				FileReader reader = new FileReader(file);
				JsonObject fileConfig = gson.fromJson(reader, JsonObject.class);
				T defaultConfig = configClass.getDeclaredConstructor().newInstance();

				JsonObject defaultConfigJson = gson.toJsonTree(defaultConfig).getAsJsonObject();

				for (Map.Entry<String, JsonElement> entry : defaultConfigJson.entrySet()) {
					if (!fileConfig.has(entry.getKey())) {
						fileConfig.add(entry.getKey(), entry.getValue());
					}
				}

				fileConfig.entrySet().removeIf(entry -> !defaultConfigJson.has(entry.getKey()));

				config = gson.fromJson(fileConfig, configClass);
				save(path, fileName);
			} catch (FileNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
			         InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void save(String path, String fileName) {
		try {
			if (!path.isEmpty()) {
				Files.createDirectories(dataPath.resolve(path));
			}

			Path filePath = dataPath.resolve(path).resolve(fileName);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}

			FileWriter writer = new FileWriter(dataPath.resolve(path).resolve(fileName).toFile());
			gson.toJson(config, writer);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
